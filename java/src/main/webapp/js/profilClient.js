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
        window.location = "indexClient.html";
    });
}

$(document).ready(function () {

    // Appel AJAX
    $.ajax({
        url: './ActionServlet',
        method: 'GET',
        data: {
            todo: 'profilClient'
        },
        dataType: 'json'
    })
            .done(function (response) { // Fonction appelée en cas d'appel AJAX réussi
                console.log('Response', response); // LOG dans Console Javascript
                if (response.connexion) {
                    $("#zodiaque").html(response.profilAstral.signeZodiaque);
                    $("#chinois").html(response.profilAstral.signeChinois);
                    $("#couleur").html(response.profilAstral.couleur);
                    $("#animal").html(response.profilAstral.animal);
                } else {
                    window.location.href = "login.html"; //Le client n'est pas connecté
                }
            })
            .fail(function (error) { // Fonction appelée en cas d'erreur lors de l'appel AJAX
                console.log('Error', error); // LOG dans Console Javascript
                alert("Erreur lors de l'appel AJAX");
            })
            .always(function () { // Fonction toujours appelée

            });
});




$("#historique").on("click", function () {
    $.ajax({
        url: './ActionServlet',
        method: 'GET',
        dataType: 'json',
        data: {'todo': 'historiqueConsultationClient'}
    }).done(function (response) {
        $('#consultations').empty();
        console.log(response);
        $('#consultations').append(
                $('<div />')
                .attr('id', 'historique')
                .attr('style', 'width: 100%; margin: auto; display: inline-block; overflow-y: auto; height: 600px;')
                .append(
                        $('<table />')
                        .attr('id', 'consultationsTable')
                        .addClass('table')
                        .append(
                                $('<thead />')
                                .append(
                                        $('<tr />')
                                        .attr('id', 'table_head')
                                        .append(
                                                $('<th />')
                                                .attr('scope', 'col')
                                                .html('Medium'),
                                                $('<th />')
                                                .attr('scope', 'col')
                                                .html('Type'),
                                                $('<th />')
                                                .attr('scope', 'col')
                                                .html('Heure de demande'),
                                                $('<th />')
                                                .attr('scope', 'col')
                                                .html('Heure de début'),
                                                $('<th />')
                                                .attr('scope', 'col')
                                                .html('Heure de fin'),
                                                )
                                        )
                                )
                        )
                );
        if (response.connexion) {
            if (typeof response.consultations === 'undefined' || response.consultations.length < 1)
            {
                return;
            } else {
                response.consultations.forEach(function (consultation) {
                    let medium = $('<td />').html(consultation.mediumDenomination);
                    let type = $('<td />').html(consultation.mediumType);
                    let heureDemande = $('<td />').html(consultation.heureDemande);
                    let heureDebut = $('<td />').html(consultation.heureDebut);
                    let heureFin = $('<td />').html(consultation.heureFin);
                    $('#consultationsTable')
                            .append(
                                    $('<tr>')
                                    .append(
                                            medium,
                                            type,
                                            heureDemande,
                                            heureDebut,
                                            heureFin
                                            )
                                    );
                });
            }
        } else {
            window.location.href = "login.html";
        }

    });
});