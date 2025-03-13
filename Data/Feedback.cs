using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;


namespace BookParadise.Data;
public class Feedback
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    public string UserName { get; set; }

    public string Email { get; set; }

    [Required]
    public string FeedbackContent { get; set; }
}
