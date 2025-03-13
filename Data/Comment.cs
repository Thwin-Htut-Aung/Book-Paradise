using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace BookParadise.Data;
public class Comment
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    public string CommentBody { get; set; }

    [Required]
    public DateTime CreatedDate { get; set; }

    [Required]
    public long UserId { get; set; }
    public required ApplicationUser User { get; set; }

    public long? BookId { get; set; }
    public Book CommentedBook { get; set; }

    public long? ChapterId { get; set; }
    public Chapter CommentedChapter { get; set; }

    public List<Reply> Replies { get; set; } = new List<Reply>();
}