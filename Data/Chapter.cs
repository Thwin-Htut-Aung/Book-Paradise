using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace BookParadise.Data;
public class Chapter
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    public required string Name { get; set; }

    public required string Type { get; set; }

    public required string FilePath { get; set; }

    public int NumberOfPages { get; set; }

    public long? BookId { get; set; }
    public Book Book { get; set; }

    public ICollection<Comment> Comments { get; set; } = new List<Comment>();
}
