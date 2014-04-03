import Database.*
import domain.*
import Engine.*

description 'kayttaja pystyy lisaamaan artikkelityyppisen viitteen'

scenario "kayttajan lisaama viite tallentuu ohjelmaan", {
    given 'viitteen lisays valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
    }

    when 'viitteen tiedot kirjattu lomakkeeseen', {
        engine.lisaaArticle("key", "author", "title", "journal", 1, 2, 1999, 4, 5)
    }

    then 'viite tallennettu ohjelmaan', {
        db.getSize().shouldBeEqual 1
    }
}

scenario "kayttaja syottama viite ei tallennu, jos siina virheita", {
    given 'viitteen lisays valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
    }

     when 'syotetaan virheellisia arvoja', {
        engine.lisaaArticle("key", "author", "title", "journal", 1, 2, 0, 99, 5)
    }

    then 'viite ei tallennu ohjelmaan', {
        engine.listaaKaikkiViitteet().size().shouldBeEqual 0
    }
}
