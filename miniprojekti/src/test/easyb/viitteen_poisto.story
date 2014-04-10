import Database.*
import domain.*
import Engine.*

description 'kayttaja voi poistaa viitteen ohjelmasta' 

scenario "kayttaja voi poistaa haluamansa viitteen", {
    given 'viitteet listattu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"fafadsdfsa", (Kentta.journal):"fasdfdsa",(Kentta.year):"1999", (Kentta.title):"fdafdsa"])
        engine.listaaKaikkiViitteet()
    } 

    when 'poistettava viite valittu', {
        db.removeEntry(0)
    }

    then 'viite on poistettu ohjelmasta', {
        engine.listaaKaikkiViitteet().length.shouldBeEqual 0
    }
}
