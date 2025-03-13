namespace BookParadise.Data
{
    public class BookPayload
    {
        public required string BookName { get; set; }
        public required string Genre { get; set; }
        public required string Author { get; set; }
        public required string Translators { get; set; }
        public required string Tags { get; set; }

        public required String CoverImagePath { get; set; }
        public required IFormFile CoverImage { get; set; }
        public List<IFormFile> Chapters { get; set; } = new();
    }
}
