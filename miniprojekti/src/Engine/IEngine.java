
package Engine;

public interface IEngine {
    public void lisaaArticle(
            String citationKey,
            String author,
            String title,
            String journal,
            int volume,
            int number,
            int year,
            int page1,
            int page2,
            String publisher,
            String address
    );
}
