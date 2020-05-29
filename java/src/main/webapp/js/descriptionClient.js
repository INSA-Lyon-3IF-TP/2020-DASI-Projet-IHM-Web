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
function demarrerConsultation() {
    let confirmation = confirm("Êtes-vous sûr(e) de vouloir démarrer la consultation ?");
    if (confirmation !== true) {
        return;
    }

    $.ajax({
        url: 'ActionServlet',
        method: 'get',
        dataType: 'json',
        data: {'todo': 'demarrerConsultation'}
    }).done(function (response) {
        if (response.success) {
            window.location = "consultationEnCours.html";
        } else {
            alert("Une erreur s'est produite. Veuillez réessayer.");
        }
    });

}

$(document).ready(function () {
    $.ajax({
        url: 'ActionServlet',
        method: 'get',
        dataType: 'json',
        data: {'todo': 'getConsultationEnAttente'}
    }).done(function (response) {
        console.log(response);
        if (!response.connexion) {
            window.location = "loginEmploye.html";
        } else if (!response.client) { //Si aucune consultation en cours pour l'employe courant
            window.location = "dashboard.html";
        } else {

            $('#medium').text(response.mediumAIncarner.mediumDenomination + ", " + response.mediumAIncarner.mediumType);
            $('#civilite').attr('value', response.client.civilite);
            $('#nom').attr('value', response.client.nom);
            $('#prenom').attr('value', response.client.prenom);
            $('#dateDeNaissance').attr('value', response.client.dateDeNaissance);
            $('#telephone').attr('value', response.client.telephone);
            $('#mail').attr('value', response.client.mail);
            $('#signeZodiaque').attr('value', response.profilAstral.signeZodiaque);
            $('#signeChinois').attr('value', response.profilAstral.signeChinois);
            $('#animal').attr('value', response.profilAstral.animal);
            $('#couleur').attr('value', response.profilAstral.couleur);


            $('#historique')
                    .append(
                            $('<table />')
                            .attr('id', 'consultationsTable')
                            .attr('class', 'table')
                            .append(
                                    $('<thead />')
                                    )
                            .append(
                                    $('<tr />')
                                    .append(
                                            $('<th />')
                                            .attr('scope', 'col')
                                            .text('Employe'),
                                            $('<th />')
                                            .attr('scope', 'col')
                                            .text('Medium'),
                                            $('<th />')
                                            .attr('scope', 'col')
                                            .text('Type'),
                                            $('<th />')
                                            .attr('scope', 'col')
                                            .text('Heure de demande'),
                                            $('<th />')
                                            .attr('scope', 'col')
                                            .text('Heure de début'),
                                            $('<th />')
                                            .attr('scope', 'col')
                                            .text('Heure de fin'),
                                            $('<th />')
                                            .attr('scope', 'col')
                                            .text('Commentaire')
                                            )
                                    )
                            );
            response.historiqueClient.forEach(function (consultation) {
                let employe = $('<td />').html(consultation.employe);
                let medium = $('<td />').html(consultation.mediumDenomination);
                let type = $('<td />').html(consultation.mediumType);
                let heureDemande = $('<td />').html(consultation.heureDemande);
                let heureDebut = $('<td />').html(consultation.heureDebut);
                let heureFin = $('<td />').html(consultation.heureFin);
                let commentaire = $('<td />').html(consultation.commentaire);

                $('#consultationsTable')
                        .append(
                                $('<tr />')
                                .append(
                                        employe,
                                        medium,
                                        type,
                                        heureDemande,
                                        heureDebut,
                                        heureFin,
                                        commentaire
                                        )
                                );
            });
        }
    });

    $('#demarrerConsultation').on("click", demarrerConsultation);
});