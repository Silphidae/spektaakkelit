import Database.*
import domain.*
import Engine.*
import static org.mockito.Mockito.*;

description 'kayttaja voi poistaa viitteen ohjelmasta' 

scenario "kayttaja voi poistaa haluamansa viitteen", {
    given 'viitteet listattu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    } 

    when 'poistettava viite valittu', {
        db.removeEntry(anyString())
    }

    then 'viite on poistettu ohjelmasta', {
        verify(db).removeEntry(anyString())
    }
}
