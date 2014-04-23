import Database.*
import domain.*
import Engine.*
import static org.mockito.Mockito.*;

description 'kayttaja pystyy lisaamaan artikkelityyppisen viitteen'

scenario "kayttajan lisaama virheeton viite tallentuu ohjelmaan", {
    given 'viitteen lisays valittu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    }

    when 'viitteen tiedot kirjattu lomakkeeseen', {
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"Pekka Pekkanen", (Kentta.journal):"Lehti",(Kentta.year):"1999", (Kentta.title):"Vuosisadan artikkeli"])
    }

    then 'viite tallennettu ohjelmaan', {
        verify(db, times(1)).insertEntry(any(Article.class))
    }
}

scenario "kayttaja syottama viite ei tallennu, jos siina virheita", {
    given 'viitteen lisays valittu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    }

     when 'syotetaan virheellisia arvoja', {
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):""])
    }

    then 'viite ei tallennu ohjelmaan', {
        verify(db, never()).insertEntry(any(Viite.class))
    }
}


