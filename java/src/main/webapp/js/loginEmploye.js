$(document).ready(function () {
    $('#bouton-connexion').on('click', function () { // Fonction appelée lors du clic sur le bouton

        console.log("clic sur le bouton de connexion"); // LOG dans Console Javascript

        // Récupération de la valeur des champs du formulaire
        let champLogin = $('#champ-login').val();
        let champPassword = $('#champ-password').val();

        // Appel AJAX
        $.ajax({
            url: './ActionServlet',
            method: 'POST',
            data: {
                todo: 'connecterEmploye',
                login: champLogin,
                password: champPassword
            },
            dataType: 'json'
        })
                .done(function (response) { // Fonction appelée en cas d'appel AJAX réussi
                    console.log('Response', response); // LOG dans Console Javascript
                    if (response.connexion) {
                        window.location = "descriptionClient.html";
                    } else {
                        alert("Erreur de Connexion");
                    }
                })
                .fail(function (error) { // Fonction appelée en cas d'erreur lors de l'appel AJAX
                    console.log('Error', error); // LOG dans Console Javascript
                    alert("Erreur lors de l'appel AJAX");
                })
                .always(function () { // Fonction toujours appelée

                });
        return false;
    });
});