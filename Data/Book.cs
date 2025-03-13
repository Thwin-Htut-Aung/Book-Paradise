using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;


namespace BookParadise.Data;

public class Book
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    public required string BookName { get; set; }

    public required string Genre { get; set; }

    public required string Author { get; set; }

    public string? Translators { get; set; }

    public string? Tags { get; set; }

    public required string CoverImagePath { get; set; }

    public bool HotTopic { get; set; } = false;

    public bool MostViewed { get; set; } = false;

    public bool Latest { get; set; } = false;

    public bool NewRelease { get; set; } = false;

    public ICollection<ApplicationUser> Bookmarkers { get; set; } = new HashSet<ApplicationUser>();

    public ICollection<Chapter> Chapters { get; set; } = new List<Chapter>();

    public ICollection<Comment> Comments { get; set; } = new List<Comment>();
}