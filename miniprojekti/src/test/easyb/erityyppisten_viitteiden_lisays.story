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
        engine.lisaaViite(Viitetyyppi.book, [(Kentta.author):"fafadsdfsa", (Kentta.publisher):"fasdfdsa",(Kentta.year):"1999", (Kentta.title):"fdafdsa"])
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
        engine.lisaaViite(Viitetyyppi.book, [(Kentta.author):"fafadsdfsa", (Kentta.publisher):"fasdfdsa",(Kentta.year):"vuosi", (Kentta.title):"fdafdsa"])
    }

    then 'viite ei tallennu ohjelmaan', {
        db.getSize().shouldBeEqual 0
    }
}

scenario "kayttajan lisaama inproceedings-viite tallentuu ohjelmaan", {
    given 'viitteen lisays valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
    }

    when 'viitteen tiedot kirjattu lomakkeeseen', {
        engine.lisaaViite(Viitetyyppi.inproceedings, [(Kentta.author):"fafadsdfsa", (Kentta.booktitle):"fasdfdsa",(Kentta.year):"1876", (Kentta.title):"fdafdsa"])
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
        engine.lisaaViite(Viitetyyppi.inproceedings, [(Kentta.author):"fafadsdfsa", (Kentta.booktitle):"fasdfdsa",(Kentta.year):"vuosi", (Kentta.title):"fdafdsa"])
    }

    then 'viite ei tallennu ohjelmaan', {
        db.getSize().shouldBeEqual 0
    }
}