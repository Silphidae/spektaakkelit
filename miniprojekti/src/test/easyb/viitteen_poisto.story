import Database.*
import miniprojekti.*
import Engine.*

description 'kayttaja voi poistaa viitteen ohjelmasta' 

scenario "kayttaja voi poistaa haluamansa viitteen", {
    given 'viitteet listattu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
        engine.lisaaArticle("key", "author", "title", "journal", 1, 2, 1993, 4, 5, "publisher", "address")
        engine.listaaKaikkiViitteet()

    } 

    when 'poistettava viite valittu', {
        db.removeEntry(0)
    }

    then 'viite on poistettu ohjelmasta', {
        db.getSize().shouldBeEqual 0
    }
}