function prendreRDV(idMedium) {
    try {
        $.ajax({
            url: 'ActionServlet',
            method: 'post',
            data: {'todo': 'prendreRDV', 'idMedium': idMedium}
        }).done(function (response) {
            if (response.success) {
                alert("La demande de rendez-vous a bien été prise en compte");
                //TODO éventuellement rediriger
            } else {
                alert("Malheureusement, ce médium n'est pas disponible. Veuillez réessayer plus tard");
            }
        }).fail(function () {
            console.log('Erreur critique');
        });
    } catch (e) {
        console.log(e);
    }
}

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
    $.ajax({
        url: 'ActionServlet',
        method: 'get',
        dataType: 'json',
        data: {'todo': 'listerMediums'}
    }).done(function (response) {

        if (response.connected) {
            $('.navbar-right').empty();
            $('.navbar-right')
                    .append(
                            $('<li />')
                            .attr('class', 'nav-item')
                            .append(
                                    $('<a />')
                                    .attr('class', 'nav-link btn')
                                    .attr('href', "profilClient.html")
                                    .text("Profil Astral")
                                    ),
                            $('<li />')
                            .attr('class', 'nav-item')
                            .append(
                                    $('<button />')
                                    .attr('class', 'nav-link btn')
                                    .text("Déconnexion")
                                    .click(disconnect)
                                    )
                            );
        }


        $('#nav-astro').append(
                $('<table />')
                .attr('id', 'astrologuesTable')
                .addClass('table')
                .append(
                        $('<thead />')
                        .append(
                                $('<tr />')
                                .attr('id', 'table_head')
                                .append(
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Dénomination'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Genre'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Présentation'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Formation'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Promo'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Prendre rendez-vous')
                                        )
                                )
                        )
                );
        $('#nav-carto').append(
                $('<table />')
                .attr('id', 'cartomanciensTable')
                .addClass('table')
                .append(
                        $('<thead />')
                        .append(
                                $('<tr />')
                                .attr('id', 'table_head')
                                .append(
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Dénomination'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Genre'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Présentation'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Prendre rendez-vous')
                                        )
                                )
                        )
                );
        $('#nav-spiri').append(
                $('<table />')
                .attr('id', 'spiritesTable')
                .addClass('table')
                .append(
                        $('<thead />')
                        .append(
                                $('<tr />')
                                .attr('id', 'table_head')
                                .append(
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Dénomination'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Genre'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Présentation'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Support'),
                                        $('<th />')
                                        .attr('scope', 'col')
                                        .html('Prendre rendez-vous')
                                        )
                                )
                        )
                );

        response.astrologues.forEach(function (astrologue) {
            let astrologueDenomination = $('<td />').html(astrologue.denomination);
            let astrologueGenre = $('<td />').html(astrologue.genre);
            let astrologuePresentation = $('<td />').html(astrologue.presentation);
            let astrologueFormation = $('<td />').html(astrologue.formation);
            let astrologuePromo = $('<td />').html(astrologue.promo);
            let astrologueBouton;
            if (response.connected) {
                astrologueBouton = $('<td />').append($('<button />').attr('class', 'btn btn-warning').html('RDV').attr('onclick', 'new function(){prendreRDV(' + astrologue.id + ')}'));
            } else {
                astrologueBouton = $('<td />').append($('<button />').attr('class', 'btn btn-warning').attr('disabled', '').html('RDV').attr('onclick', 'alert("lol")'));
            }

            $('#astrologuesTable')
                    .append(
                            $('<tr>')
                            .append(
                                    astrologueDenomination,
                                    astrologueGenre,
                                    astrologuePresentation,
                                    astrologueFormation,
                                    astrologuePromo,
                                    astrologueBouton
                                    )
                            );
        });
        response.cartomanciens.forEach(function (cartomancien) {
            let cartomancienDenomination = $('<td />').html(cartomancien.denomination);
            let cartomancienGenre = $('<td />').html(cartomancien.genre);
            let cartomancienPresentation = $('<td />').html(cartomancien.presentation);
            let cartomancienBouton;
            if (response.connected) {
                cartomancienBouton = $('<td />').append($('<button />').attr('class', 'btn btn-warning').html('RDV').attr('onclick', 'new function(){prendreRDV(' + cartomancien.id + ')}'));
            } else {
                cartomancienBouton = $('<td />').append($('<button />').attr('class', 'btn btn-warning').attr('disabled', '').html('RDV').attr('onclick', 'alert("lol")'));
            }
            $('#cartomanciensTable')
                    .append(
                            $('<tr>')
                            .append(
                                    cartomancienDenomination,
                                    cartomancienGenre,
                                    cartomancienPresentation,
                                    cartomancienBouton
                                    )
                            );
        });
        response.spirites.forEach(function (spirite) {
            let spiriteDenomination = $('<td />').html(spirite.denomination);
            let spiriteGenre = $('<td />').html(spirite.genre);
            let spiritePresentation = $('<td />').html(spirite.presentation);
            let spiriteSupport = $('<td />').html(spirite.support);
            let spiriteBouton;
            if (response.connected) {
                spiriteBouton = $('<td />').append($('<button />').attr('class', 'btn btn-warning').html('RDV').attr('onclick', 'new function(){prendreRDV(' + spirite.id + ')}'));
            } else {
                spiriteBouton = $('<td />').append($('<button />').attr('class', 'btn btn-warning').attr('disabled', '').html('RDV').attr('onclick', 'alert("lol")'));
            }
            $('#spiritesTable')
                    .append(
                            $('<tr>')
                            .append(
                                    spiriteDenomination,
                                    spiriteGenre,
                                    spiritePresentation,
                                    spiriteSupport,
                                    spiriteBouton
                                    )
                            );
        });
    });
    $("#loading").remove();
});