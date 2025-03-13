using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;


namespace BookParadise.Data;

public class Reply
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    public string ReplyBody { get; set; }

    [Required]
    public DateTime CreatedDate { get; set; }

    [Required]
    public long UserId { get; set; }
    public ApplicationUser User { get; set; }

    public long? CommentId { get; set; }
    public Comment RepliedComment { get; set; }
}