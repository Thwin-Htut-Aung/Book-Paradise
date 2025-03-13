using BookParadise.Data;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.ViewFeatures;
using Microsoft.EntityFrameworkCore;



namespace BookParadise.Controllers
{
    
    [Route("api/[controller]")]
    [ApiController]
    public class BookController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly IWebHostEnvironment _env;
        private readonly ILogger<BookController> _logger;

        public BookController(ApplicationDbContext context)
        {
            _context = context;
            _context.Database.EnsureCreated();
        }

        [HttpPost("add-bookmarks")]
        public async Task<IActionResult> AddBookmarks([FromBody] List<string> bookNames, [FromQuery] string userEmail)
        {
            var user = await _context.Users.Include(u => u.Bookmarks).FirstOrDefaultAsync(u => u.Email == userEmail);
            if (user == null) return NotFound("User not found");

            var books = await _context.Books.Where(b => bookNames.Contains(b.BookName)).ToListAsync();
            foreach (var book in books)
            {
                user.Bookmarks.Add(book);
            }
            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpGet("bookmarks")]
        public async Task<IActionResult> GetBookmarks([FromQuery] string userEmail, [FromQuery] string genre = "All Genres")
        {
            var user = await _context.Users.Include(u => u.Bookmarks).FirstOrDefaultAsync(u => u.Email == userEmail);
            if (user == null) return NotFound("User not found");

            var bookmarks = genre == "All Genres" ? user.Bookmarks : user.Bookmarks.Where(b => b.Genre == genre).ToList();

            return Ok(new { Bookmarks = bookmarks, EmptyState = bookmarks.Count != 0 ? null : "empty" });
        }

        [HttpDelete("delete-bookmark")]
        public async Task<IActionResult> DeleteBookmark([FromQuery] string bookName, [FromQuery] string userEmail)
        {
            var user = await _context.Users.Include(u => u.Bookmarks).FirstOrDefaultAsync(u => u.Email == userEmail);
            if (user == null) return NotFound("User not found");

            var book = user.Bookmarks.FirstOrDefault(b => b.BookName == bookName);
            if (book == null) return NotFound("Book not found");

            user.Bookmarks.Remove(book);
            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpGet("view-all-books")]
        public async Task<IActionResult> GetAllBooks([FromQuery] string genre = "All Genres")
        {
            var books = genre == "All Genres" ? await _context.Books.ToListAsync() : await _context.Books.Where(b => b.Genre == genre).ToListAsync();
            return Ok(books);
        }

        [HttpGet("view-book")]
        public async Task<IActionResult> GetBook([FromQuery] string bookName, [FromQuery] string userEmail)
        {
            var book = await _context.Books.Include(b => b.Comments).FirstOrDefaultAsync(b => b.BookName == bookName);
            if (book == null) return NotFound("Book not found");

            var user = await _context.Users.Include(u => u.Bookmarks).FirstOrDefaultAsync(u => u.Email == userEmail);
            var isBookmarked = user?.Bookmarks.Any(b => b.BookName == bookName) ?? false;

            return Ok(new { Book = book, BookmarkStatus = isBookmarked ? "Yes" : "No" });
        }

        [HttpGet("read-chapter")]
        public async Task<IActionResult> ReadChapter([FromQuery] string chapterName)
        {
            var chapter = await _context.Chapters.Include(c => c.Comments).FirstOrDefaultAsync(c => c.Name == chapterName);
            if (chapter == null) return NotFound("Chapter not found");

            var pageNumbers = Enumerable.Range(1, chapter.NumberOfPages).ToList();
            return Ok(new { Chapter = chapter, PageNumbers = pageNumbers });
        }

        [HttpPost("post-comment")]
        public async Task<IActionResult> PostComment([FromForm] string userEmail, [FromForm] string target, [FromForm] string contentName, [FromForm] string commentBody)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.Email == userEmail);
            if (user == null) return NotFound("User not found");

            var comment = new Comment
            {
                User = user,
                CreatedDate = DateTime.UtcNow,
                CommentBody = commentBody
            };

            if (target == "book")
            {
                var book = await _context.Books.FirstOrDefaultAsync(b => b.BookName == contentName);
                if (book == null) return NotFound("Book not found");
                comment.CommentedBook = book;
            }
            else
            {
                var chapter = await _context.Chapters.FirstOrDefaultAsync(c => c.Name == contentName);
                if (chapter == null) return NotFound("Chapter not found");
                comment.CommentedChapter = chapter;
            }

            _context.Comments.Add(comment);
            await _context.SaveChangesAsync();

            return Ok();
        }

        [HttpDelete("delete-comment")]
        public async Task<IActionResult> DeleteComment([FromQuery] long commentId, [FromQuery] string type)
        {
            if (type == "comment")
            {
                var comment = await _context.Comments.FindAsync(commentId);
                if (comment == null) return NotFound("Comment not found");
                _context.Comments.Remove(comment);
            }
            else
            {
                var reply = await _context.Replies.FindAsync(commentId);
                if (reply == null) return NotFound("Reply not found");
                _context.Replies.Remove(reply);
            }

            await _context.SaveChangesAsync();
            return Ok();
        }


        [HttpPost("post-reply")]
        public async Task<IActionResult> PostReply([FromQuery] string userEmail, [FromQuery] long commentId, [FromForm] string replyBody)
        {
            var user = await _context.Users.FirstOrDefaultAsync(u => u.Email == userEmail);
            if (user == null) return NotFound("User not found");

            var comment = await _context.Comments.FindAsync(commentId);
            if (comment == null) return NotFound("Comment not found");

            var reply = new Reply
            {
                User = user,
                CreatedDate = DateTime.UtcNow,
                ReplyBody = replyBody,
                RepliedComment = comment
            };

            _context.Replies.Add(reply);
            await _context.SaveChangesAsync();

            return Ok();
        }


        [Authorize(Roles = "Admin")]
        [HttpPost("upload-book")]
        public async Task<IActionResult> UploadBook([FromForm] BookPayload payload)
        {
            string basePath = Path.Combine(_env.WebRootPath, "static");

            // Save Cover Image
            string coverImagePath = Path.Combine(basePath, "cover_images", $"{payload.BookName}-cover.jpg");
            using (var stream = new FileStream(coverImagePath, FileMode.Create))
            {
                await payload.CoverImage.CopyToAsync(stream);
            }

            //Save Book
            Book book = new()
            {
                BookName = payload.BookName,
                Genre = payload.Genre,
                Author = payload.Author,
                Translators = payload.Translators,
                Tags = payload.Tags,
                CoverImagePath = coverImagePath,
            };

            // Save Chapters
            List<Chapter> chapters = new();
            string chaptersPath = Path.Combine(basePath, "novel_chapters");
            foreach (var chapterFile in payload.Chapters.Where(c => c != null))
            {
                string filePath = Path.Combine(chaptersPath, $"{payload.BookName}-{chapterFile.FileName}");
                using (var stream = new FileStream(filePath, FileMode.Create))
                {
                    await chapterFile.CopyToAsync(stream);
                }

                Chapter chapter = new()
                {
                    Name = $"{payload.BookName}-{chapterFile.FileName}",
                    FilePath = filePath,
                    Type = chapterFile.ContentType,
                    Book = book

                };
                chapters.Add(chapter);
                _context.Chapters.Add(chapter);

            }

            book.Chapters = chapters;
            
            _context.Books.Add(book);
            await _context.SaveChangesAsync();
            return Ok(new { message = "Book uploaded successfully!" });
        }


        [Authorize(Roles = "Admin")]
        [HttpPost("upload-chapter")]
        public async Task<IActionResult> UploadChapter([FromForm] IFormFile newChapter, [FromQuery] string bookName)
        {
            var book = await _context.Books.Include(b => b.Chapters).FirstOrDefaultAsync(b => b.BookName == bookName);
            if (book == null) return NotFound("Book not found.");

            string chaptersPath = Path.Combine(_env.WebRootPath, "static", "novel_chapters");
            string filePath = Path.Combine(chaptersPath, $"{bookName}-{newChapter.FileName}");

            using (var stream = new FileStream(filePath, FileMode.Create))
            {
                await newChapter.CopyToAsync(stream);
            }

            Chapter chapter = new()
            {
                Name = $"{bookName}-{newChapter.FileName}",
                FilePath = filePath,
                Type = newChapter.ContentType,
                Book = book
            };

            _context.Chapters.Add(chapter);
            await _context.SaveChangesAsync();

            return Ok(new { message = "Chapter uploaded successfully!" });
        }


        [Authorize(Roles = "Admin")]
        [HttpDelete("delete-book")]
        public async Task<IActionResult> DeleteBook([FromQuery] string bookName)
        {
            var book = await _context.Books.Include(b => b.Chapters).FirstOrDefaultAsync(b => b.BookName == bookName);
            if (book == null) return NotFound("Book not found.");

            string basePath = Path.Combine(_env.WebRootPath, "static");

            // Delete Cover Image
            string coverImagePath = Path.Combine(basePath, "cover_images", $"{bookName}-cover.jpg");
            bool coverDeleted = DeleteFile(coverImagePath);

            int failedChapters = 0;
            int failedPages = 0;

            // Delete Chapters & Their Pages
            foreach (var chapter in book.Chapters)
            {
                string chapterPath = Path.Combine(basePath, "novel_chapters", chapter.Name);
                if (!DeleteFile(chapterPath)) failedChapters++;

                for (int i = 1; i <= chapter.NumberOfPages; i++)
                {
                    string pagePath = Path.Combine(basePath, "novel_pages", $"{chapter.Name}-page{i}.jpg");
                    if (!DeleteFile(pagePath)) failedPages++;
                }
            }

            _context.Books.Remove(book);
            await _context.SaveChangesAsync();

            string message = "Book deleted successfully!";
            if (failedChapters > 0 || failedPages > 0 || !coverDeleted)
                message = $"{failedChapters} chapters, {failedPages} pages, or the cover image could not be deleted.";

            return Ok(new { message });
        }


        [Authorize(Roles = "Admin")]
        [HttpDelete("delete-chapter")]
        public async Task<IActionResult> DeleteChapter([FromQuery] string chapterName)
        {
            var chapter = await _context.Chapters.Include(c => c.Book).FirstOrDefaultAsync(c => c.Name == chapterName);
            if (chapter == null) return NotFound("Chapter not found.");

            string basePath = Path.Combine(_env.WebRootPath, "static");

            // Delete Chapter File
            string chapterPath = Path.Combine(basePath, "novel_chapters", chapter.Name);
            bool chapterDeleted = DeleteFile(chapterPath);

            // Delete Pages
            int failedPages = 0;
            for (int i = 1; i <= chapter.NumberOfPages; i++)
            {
                string pagePath = Path.Combine(basePath, "novel_pages", $"{chapter.Name}-page{i}.jpg");
                if (!DeleteFile(pagePath)) failedPages++;
            }

            _context.Chapters.Remove(chapter);
            await _context.SaveChangesAsync();

            string message = chapterDeleted
                ? "Chapter deleted successfully!"
                : "Chapter file deletion failed.";

            if (failedPages > 0)
                message += $" {failedPages} pages were not deleted.";

            return Ok(new { message });
        }

        private bool DeleteFile(string path)
        {
            try
            {
                if (System.IO.File.Exists(path))
                {
                    System.IO.File.Delete(path);
                    return true;
                }
            }
            catch (Exception ex)
            {
                _logger.LogError($"Error deleting file {path}: {ex.Message}");
            }
            return false;
        }


        [Authorize(Roles = "Admin")]
        [HttpPost("add-to-category")]
        public async Task<IActionResult> AddToCategory([FromForm] string bookName, [FromQuery] string category)
        {
            if (string.IsNullOrEmpty(bookName) || string.IsNullOrEmpty(category))
            {
                return Redirect("/");
            }

            var book = await _context.Books.FirstOrDefaultAsync(b => b.BookName == bookName);
            if (book == null)
            {
                return Redirect("/");
            }

            switch (category)
            {
                case "HotTopics": book.HotTopic = true; break;
                case "MostViewed": book.MostViewed = true; break;
                case "LatestUpdates": book.Latest = true; break;
                case "NewReleases": book.NewRelease = true; break;
                default:

                    return Redirect("/");
            }

            await _context.SaveChangesAsync();

            return Redirect("/");
        }


        [Authorize(Roles = "Admin")]
        [HttpPost("remove-from-category")]
        public async Task<IActionResult> RemoveFromCategory([FromForm] List<string> BookNames, [FromForm] string Category)
        {
            if (BookNames == null || BookNames.Count == 0 || string.IsNullOrEmpty(Category))
            {
                return Redirect("/");
            }

            var books = await _context.Books.Where(b => BookNames.Contains(b.BookName)).ToListAsync();
            if (books.Count == 0)
            {
                return Redirect("/");
            }

            foreach (var book in books)
            {
                switch (Category)
                {
                    case "Hot-Topics": book.HotTopic = false; break;
                    case "Most-Viewed": book.MostViewed = false; break;
                    case "Latest-Updates": book.Latest = false; break;
                    case "New-Releases": book.NewRelease = false; break;
                    default:
                        return Redirect("/");
                }
            }

            await _context.SaveChangesAsync();
            return Redirect("/");
        }

        [HttpPost("submit-feedback")]
        public async Task<IActionResult> SubmitFeedback([FromForm] Feedback feedback)
        {
            if (string.IsNullOrWhiteSpace(feedback.Email) || string.IsNullOrWhiteSpace(feedback.FeedbackContent))
            {
                ViewData["error_msg"] = "You must write your name and a piece of feedback.";
            }
            else
            {
                _context.Feedback.Add(feedback);
                await _context.SaveChangesAsync();
                ViewData["success_msg"] = "Thank you for your feedback!";
            }

            return Redirect("/contact-us");
        }

    }
}

