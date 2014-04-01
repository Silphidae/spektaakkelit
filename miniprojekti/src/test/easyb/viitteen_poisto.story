import Database.*
import miniprojekti.*
import Engine.*

description 'kayttaja voi poistaa viitteen ohjelmasta' 

scenario "kayttaja voi poistaa haluamansa viitteen", {
    given 'viitteet listattu'
    when 'poistettava viite valittu'
    then 'viite on poistettu ohjelmasta'
}