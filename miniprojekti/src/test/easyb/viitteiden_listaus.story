import Database.*
import domain.*
import Engine.*

description 'kayttaja voi listata viitteet' 

scenario "kayttaja voi listata viitteet", {
    given 'ohjelmassa viitteita', {
        db = new MockDatabase()
        engine = new EngineStub(db)
        engine.lisaaArticle("key", "testihenkilö", "testaustieto", "valitut palat", 12, 212, 1672, 68, 99)
        engine.lisaaArticle("key2", "testihenkilö", "testaustieto", "valitut palat", 12, 212, 1672, 68, 99)
        
    } 

    when 'kayttaja valitsee viitteiden listauksen', {
        lista = engine.listaaKaikkiViitteet()
    }

    then 'viitteet nakyvat listana', {
        lista.size().shouldBeEqual 2
    }
}
