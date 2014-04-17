import Database.*
import domain.*
import Engine.*
import Syotetarkistus.*
import static org.mockito.Mockito.*;

description 'kayttaja voi listata viitteet' 

scenario "kayttaja voi listata viitteet", {
    given 'ohjelmassa viitteita', {
        db = mock(Database.class)
        engine = new EngineStub(db)

        viite1 = new Article(new Syotetarkastaja())
        viite1.lisaaCitationKey("avain1")
        viite1.lisaaKentta(Kentta.author, "Kauko Kirjoittaja")
        viite1.lisaaKentta(Kentta.title, "Nimi")
        viite1.lisaaKentta(Kentta.year, "1987")
        viite1.lisaaKentta(Kentta.journal, "Lehti")

        viite2 = new Book(new Syotetarkastaja())
        viite2.lisaaCitationKey("avain2")
        viite2.lisaaKentta(Kentta.editor, "Touko Toimittaja")
        viite2.lisaaKentta(Kentta.title, "Nimeke")
        viite2.lisaaKentta(Kentta.year, "1975")
        viite2.lisaaKentta(Kentta.publisher, "Julkaisija")

        viitteet = new ArrayList<Viite>()
        viitteet.add(viite1)
        viitteet.add(viite2)

        when(db.getAllEntries()).thenReturn viitteet
    } 

    when 'kayttaja valitsee viitteiden listauksen', {
        lista = engine.listaaKaikkiViitteet()
    }

    then 'viitteet nakyvat listana', {
        lista.length.shouldBeEqual 2
    }
}
