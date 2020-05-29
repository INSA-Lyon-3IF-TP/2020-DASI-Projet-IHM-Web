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

window.chartColors = {
    red: 'rgb(255, 99, 132)',
    orange: 'rgb(255, 159, 64)',
    yellow: 'rgb(255, 205, 86)',
    green: 'rgb(75, 192, 192)',
    blue: 'rgb(54, 162, 235)',
    purple: 'rgb(153, 102, 255)',
    grey: 'rgb(201, 203, 207)'
};

$(document).ready(function () {
    $.ajax({
        url: 'ActionServlet',
        method: 'get',
        dataType: 'json',
        data: {'todo': 'getStatistics'}
    }).done(function (response) {
        if (!response.connexion) {
            window.location = "loginEmploye.html";
        } else {
            if (response.occupe) {
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

            let ctx1 = document.getElementById('canvas1').getContext('2d');

            let backgroundColor = [
                window.chartColors.red,
                window.chartColors.orange,
                window.chartColors.yellow,
                window.chartColors.green,
                window.chartColors.blue,
                window.chartColors.red,
                window.chartColors.orange,
                window.chartColors.yellow,
                window.chartColors.green,
                window.chartColors.blue,
                window.chartColors.red,
                window.chartColors.orange,
                window.chartColors.yellow,
                window.chartColors.green,
                window.chartColors.blue,
                window.chartColors.red,
                window.chartColors.orange,
                window.chartColors.yellow,
                window.chartColors.green,
                window.chartColors.blue,
                window.chartColors.red,
                window.chartColors.orange,
                window.chartColors.yellow,
                window.chartColors.green,
                window.chartColors.blue,
                window.chartColors.red,
                window.chartColors.orange,
                window.chartColors.yellow,
                window.chartColors.green,
                window.chartColors.blue,
                window.chartColors.red,
                window.chartColors.orange,
                window.chartColors.yellow,
                window.chartColors.green,
                window.chartColors.blue
            ];

            response.dataTop5.datasets[0].backgroundColor = backgroundColor;

            let top5Mediums = new Chart(ctx1, {
                type: 'pie',
                data: response.dataTop5,
                options: {
                    responsive: true
                }
            });

            let ctx2 = document.getElementById('canvas2').getContext('2d');
            response.dataRepartitionClients.datasets[0].backgroundColor = backgroundColor;
            let nbClientsParEmploye = new Chart(ctx2, {
                type: 'bar',
                data: response.dataRepartitionClients,
                options: {
                    legend: false
                }
            });

            let ctx3 = document.getElementById('canvas3').getContext('2d');
            response.dataAllMediums.datasets[0].backgroundColor = backgroundColor;
            let nbConsultationsParMedium = new Chart(ctx3, {
                type: 'bar',
                data: response.dataAllMediums,
                options: {
                    legend: false
                }
            });
            /*
             let ctx4 = document.getElementById('canvas4').getContext('2d');                                        
             let allMediumsPie = new Chart(ctx4, {
             type: 'pie',
             data: response.dataAllMediums,
             options: {
             responsive: true
             }
             });
             */
        }
    });
});
