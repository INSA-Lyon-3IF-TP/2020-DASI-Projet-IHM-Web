function disconnect() {
    $.ajax({
        url: 'ActionServlet',
        method: 'get',
        dataType: 'json',
        data: {'todo': 'logout'}
    }).done(function (response) {
        if (!response.success) {
            alert('Erreur de déconnexion');
        }
        window.location = "loginEmploye.html";
    });
}

$(document).ready(function () {

    // Appel AJAX
    $.ajax({
        url: './ActionServlet',
        method: 'GET',
        data: {
            todo: 'profilEmploye'
        },
        dataType: 'json'
    })
            .done(function (response) { // Fonction appelée en cas d'appel AJAX réussi
                console.log('Response', response); // LOG dans Console Javascript
                if (response.connexion) {
                    if (response.employe.occupe) {
                        $('.navbar-right')
                                .prepend(
                                        $('<li />')
                                        .attr('class', 'nav-item')
                                        .append(
                                                $('<a />')
                                                .attr('class', 'btn btn-primary')
                                                .attr('href', 'descriptionClient.html')
                                                .text("Consultation en attente")
                                                )
                                        );
                    }
                    $("#nom").html(response.employe.nom);
                    $("#prenom").html(response.employe.prenom);
                    $("#tel").html(response.employe.tel);
                    $("#adresse").html(response.employe.adresse);
                    $("#mail").html(response.employe.mail);
                    $('#consultations').empty();
                    $('#consultations').append(
                            $('<div />')
                            .attr('id', 'historique')
                            .attr('style', 'width: 100%; margin: auto; display: inline-block; overflow-y: auto; height: 500px;')
                            .append(
                                    $('<table />')
                                    .attr('id', 'consultationsTable')
                                    .addClass("table")
                                    .append(
                                            $('<thead />')
                                            .append(
                                                    $('<tr />')
                                                    .attr('id', 'table_head')
                                                    .append(
                                                            $('<th />')
                                                            .attr('scope', 'col')
                                                            .html('Client'),
                                                            $('<th />')
                                                            .attr('scope', 'col')
                                                            .html('Medium'),
                                                            $('<th />')
                                                            .attr('scope', 'col')
                                                            .html('Type'),
                                                            $('<th />')
                                                            .attr('scope', 'col')
                                                            .html('Heure de début'),
                                                            $('<th />')
                                                            .attr('scope', 'col')
                                                            .html('Heure de fin'),
                                                            $('<th />')
                                                            .attr('scope', 'col')
                                                            .html('Commentaire')
                                                            ),
                                                    )
                                            )
                                    )
                            );

                    if (typeof response.consultations === 'undefined' || response.consultations.length < 1)
                    {
                        return;
                    } else {
                        response.consultations.forEach(function (consultation) {
                            let medium = $('<td />').html(consultation.mediumDenomination);
                            let type = $('<td />').html(consultation.mediumType);
                            let heureDebut = $('<td />').html(consultation.heureDebut);
                            let heureFin = $('<td />').html(consultation.heureFin);
                            let client = $('<td />').html(consultation.client);
                            let commentaire = $('<td />').html(consultation.commentaire);
                            $('#consultationsTable')
                                    .append(
                                            $('<tr>')
                                            .append(
                                                    client,
                                                    medium,
                                                    type,
                                                    heureDebut,
                                                    heureFin,
                                                    commentaire
                                                    )
                                            );
                        });
                    }
                } else {
                    window.location.href = "loginEmploye.html"; //L'employe n'est pas connecté
                }
            })
            .fail(function (error) { // Fonction appelée en cas d'erreur lors de l'appel AJAX
                console.log('Error', error); // LOG dans Console Javascript
                alert("Erreur lors de l'appel AJAX");
            })
            .always(function () { // Fonction toujours appelée

            });
});