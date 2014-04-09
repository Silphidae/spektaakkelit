import Database.*
import domain.*
import Engine.*

description 'kayttaja pystyy lisaamaan erityyppisia viitteita ohjelmaan'

scenario "kayttajan lisaama kirjaviite tallentuu ohjelmaan", {
    given 'viitteen lisays valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
    }

    when 'viitteen tiedot kirjattu lomakkeeseen', {
        engine.lisaaBook("key", "author", "title", "published", 1999)
    }

    then 'viite tallennettu ohjelmaan', {
        db.getSize().shouldBeEqual 1
    }
}

scenario "kayttaja syottama kirjaviite ei tallennu, jos siina virheita", {
    given 'viitteen lisays valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
    }

    when 'syotetaan virheellisia arvoja', {
        engine.lisaaBook("key", "author", "title", "", 1200)
    }

    then 'viite ei tallennu ohjelmaan', {
        db.getSize().shouldBeEqual 0
    }
}

scenario "kayttajan lisaama proceedings-viite tallentuu ohjelmaan", {
    given 'viitteen lisays valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
    }

    when 'viitteen tiedot kirjattu lomakkeeseen', {
        engine.lisaaInProceedings("key", "author", "title", "booktitle", 1999)
    }

    then 'viite tallennettu ohjelmaan', {
        db.getSize().shouldBeEqual 1
    }
}

scenario "kayttaja syottama proceedings-viite ei tallennu, jos siina virheita", {
    given 'viitteen lisays valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
    }

    when 'syotetaan virheellisia arvoja', {
        engine.lisaaInProceedings("key", "", "title", "booktitle", 1111)
    }

    then 'viite ei tallennu ohjelmaan', {
        db.getSize().shouldBeEqual 0
    }
}