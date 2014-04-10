import Database.*
import domain.*
import Engine.*

description 'kayttaja voi listata viitteet' 

scenario "kayttaja voi listata viitteet", {
    given 'ohjelmassa viitteita', {
        db = new MockDatabase()
        engine = new EngineStub(db)
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"fafadsdfsa", (Kentta.journal):"fasdfdsa",(Kentta.year):"1999", (Kentta.title):"fdafdsa"])
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"fafadsdfsa", (Kentta.journal):"fasdfdsa",(Kentta.year):"2000", (Kentta.title):"fdafdsa"])
    } 

    when 'kayttaja valitsee viitteiden listauksen', {
        lista = engine.listaaKaikkiViitteet()
    }

    then 'viitteet nakyvat listana', {
        lista.length.shouldBeEqual 2
    }
}
