$(document).ready(function () {
    $.ajax({
        url: 'ActionServlet',
        method: 'GET',
        dataType: 'json',
        data: {'todo': 'getConsultationEnCours'}
    }).done(function (response) {
        if (!response.connexion) {
            window.location = "loginEmploye.html";
        } else {
            console.log(response);
            $('#medium').text(response.mediumDenomination + ", " + response.mediumType);
            $('#commentaire').html(response.consultation.commentaire);
        }
    });

    $("#predictions").on("click", function () {
        var noteAmour = $("#amourId").val();
        var noteSante = $("#santeId").val();
        var noteTravail = $("#travailId").val();
        console.log(noteAmour);
        console.log(noteSante);
        console.log(noteTravail);
        if (noteAmour < 1 || noteAmour > 4 || noteAmour === undefined
                || noteSante < 1 || noteSante > 4 || noteAmour === undefined
                || noteTravail < 1 || noteTravail > 4 || noteAmour === undefined)
        {
            alert("Notes incorrects");
            return;
        }

        $.ajax({
            url: './ActionServlet',
            method: 'POST',
            data: {
                'todo': 'getPredictions',
                'noteAmour': noteAmour,
                'noteSante': noteSante,
                'noteTravail': noteTravail
            },
            dataType: 'json'
        })
                .done(function (response) { // Fonction appelée en cas d'appel AJAX réussi
                    console.log('Response', response); // LOG dans Console Javascript
                    if (!response.connexion) {
                        window.location = "loginEmploye.html";
                    } else {
                        $("#predictionAmour").html(response.predictionAmour);
                        $("#predictionSante").html(response.predictionSante);
                        $("#predictionTravail").html(response.predictionTravail);
                    }
                })
                .fail(function (error) { // Fonction appelée en cas d'erreur lors de l'appel AJAX
                    console.log('Error', error); // LOG dans Console Javascript
                    alert("Erreur lors de l'appel AJAX");
                })
                .always(function () { // Fonction toujours appelée

                });
    });

    $("#terminerConsultation").on("click", function () {
        var commentaire = $.trim($("#commentaire").val());

        $.ajax({
            url: './ActionServlet',
            method: 'POST',
            data: {
                'todo': 'terminerConsultation',
                'commentaire': commentaire
            },
            dataType: 'json'
        })
                .done(function (response) { // Fonction appelée en cas d'appel AJAX réussi
                    console.log('Response', response); // LOG dans Console Javascript
                    if (!response.connexion) {
                        window.location = "loginEmploye.html";
                    } else {
                        window.location = "dashboard.html";
                    }
                })
                .fail(function (error) { // Fonction appelée en cas d'erreur lors de l'appel AJAX
                    console.log('Error', error); // LOG dans Console Javascript
                    alert("Erreur lors de l'appel AJAX");
                })
                .always(function () { // Fonction toujours appelée

                });
    });
});