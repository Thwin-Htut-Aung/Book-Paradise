using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace BookParadise.Data;

public class ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : IdentityDbContext<ApplicationUser>(options)
{
    public DbSet<Book> Books { get; set; }
    public DbSet<ApplicationUser> Users { get; set; }
    public DbSet<Chapter> Chapters { get; set; }
    public DbSet<Comment> Comments { get; set; }

    public DbSet<Reply> Replies { get; set; }

    public DbSet<Feedback> Feedback { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Book>()
            .HasMany(b => b.Bookmarkers)
            .WithMany(u => u.Bookmarks);

        modelBuilder.Entity<Book>()
            .HasMany(b => b.Chapters)
            .WithOne(c => c.Book)
            .HasForeignKey(c => c.BookId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<Book>()
            .HasMany(b => b.Comments)
            .WithOne(c => c.CommentedBook)
            .HasForeignKey(c => c.Id)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<Comment>()
            .HasMany(c => c.Replies)
            .WithOne(r => r.RepliedComment)
            .HasForeignKey(c => c.Id)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<Chapter>()
            .HasMany(c => c.Comments)
            .WithOne(c => c.CommentedChapter)
            .HasForeignKey(c => c.Id)
            .OnDelete(DeleteBehavior.Cascade);
    }
}

