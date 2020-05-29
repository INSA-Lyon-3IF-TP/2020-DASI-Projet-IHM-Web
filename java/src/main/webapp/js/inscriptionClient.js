$(document).ready(function () {
    $('#bouton-inscription').on('click', function () { // Fonction appelée lors du clic sur le bouton

        console.log("clic sur le bouton de insciption"); // LOG dans Console Javascript

        // Récupération de la valeur des champs du formulaire
        var champPassword = $('#champ-password').val();
        var champPasswordConfirmation = $('#champ-password-confirmation').val();

        if (champPassword !== champPasswordConfirmation)
        {
            alert("Les 2 mots de passe ne sont pas identiques");
            return;
        }

        var champNom = $('#champ-nom').val();
        var champPrenom = $('#champ-prenom').val();
        var champMail = $('#champ-mail').val();
        var champDate = $('#champ-date').val();
        var champCivilite = $('#champ-civilite').val();
        var champTelephone = $('#champ-telephone').val();
        var champAdresse = $('#champ-adresse').val();

        if (champNom.length === 0
                || champPrenom.length === 0
                || champMail.length === 0
                || champDate.length === 0
                || champCivilite.length === 0
                || champTelephone.length === 0
                || champAdresse.length === 0
                || champPassword.length === 0) //Vérification côté client
        {
            alert("Tous les champs doivent être remplis");
            return false;
        }
        // Appel AJAX
        $.ajax({
            url: './ActionServlet',
            method: 'POST',
            data: {
                todo: 'inscription',
                nom: champNom,
                prenom: champPrenom,
                mail: champMail,
                date: champDate,
                civilite: champCivilite,
                tel: champTelephone,
                adresse: champAdresse,
                password: champPassword,
                passwordConfirmation: champPasswordConfirmation
            },
            dataType: 'json'
        })
                .done(function (response) { // Fonction appelée en cas d'appel AJAX réussi
                    console.log('Response', response); // LOG dans Console Javascript
                    if (response.inscription) {
                        alert("Succès de l'inscription");
                        window.location.href = "login.html";
                    } else {
                        alert("Erreur d'Inscription");
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