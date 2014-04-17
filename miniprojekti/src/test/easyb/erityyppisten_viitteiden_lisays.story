import Database.*
import domain.*
import Engine.*
import static org.mockito.Mockito.*;

description 'kayttaja pystyy lisaamaan erityyppisia viitteita ohjelmaan'

scenario "kayttajan lisaama kirjaviite tallentuu ohjelmaan", {
    given 'viitteen lisays valittu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    }

    when 'viitteen tiedot kirjattu lomakkeeseen', {
        engine.lisaaViite(Viitetyyppi.book, [(Kentta.author):"Pekka Pulliainen", (Kentta.publisher):"Julkaisija",(Kentta.year):"1999", (Kentta.title):"Nimeke"])
    }

    then 'viite tallennettu ohjelmaan', {
        verify(db, times(1)).insertEntry(any(Book.class))
    }
}

scenario "kayttaja syottama kirjaviite ei tallennu, jos siina virheita", {
    given 'viitteen lisays valittu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    }

    when 'syotetaan virheellisia arvoja', {
        engine.lisaaViite(Viitetyyppi.book, [(Kentta.author):"Pekka Pulliainen", (Kentta.publisher):"Julkaisija",(Kentta.year):"vuosi", (Kentta.title):"Nimeke"])
    }

    then 'viite ei tallennu ohjelmaan', {
        verify(db, never()).insertEntry(any(Viite.class))
    }
}

scenario "kayttajan lisaama inproceedings-viite tallentuu ohjelmaan", {
    given 'viitteen lisays valittu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    }

    when 'viitteen tiedot kirjattu lomakkeeseen', {
        engine.lisaaViite(Viitetyyppi.inproceedings, [(Kentta.author):"Pekka Pulliainen", (Kentta.booktitle):"Kirjan nimi",(Kentta.year):"1876", (Kentta.title):"Nimi"])
    }

    then 'viite tallennettu ohjelmaan', {
        verify(db, times(1)).insertEntry(any(InProceedings.class))
    }
}

scenario "kayttaja syottama proceedings-viite ei tallennu, jos siina virheita", {
    given 'viitteen lisays valittu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    }

    when 'syotetaan virheellisia arvoja', {
        engine.lisaaViite(Viitetyyppi.inproceedings, [(Kentta.author):"Pekka Pulliainen", (Kentta.booktitle):"Kirjan nimi",(Kentta.year):"vuosi", (Kentta.title):"Nimi"])
    }

    then 'viite ei tallennu ohjelmaan', {
        verify(db, never()).insertEntry(any(Viite.class))
    }
}