let buttonContainer = $("#buttonContainer");
let buttonHTML = buttonContainer.html();

function shorten() {
    clearAlerts();

    buttonContainer.html('<div class=\"wobblebar-loader\"></div>');

    let protocol = $(".form-select").val();

    let url = $("#basic-url").val();
    setTimeout(() => getShortenLink(protocol + url), 500);
}

function timeSince(date) {

    let seconds = Math.floor((new Date() - date) / 1000);

    let interval = seconds / 31536000;

    if (interval > 1) {
        return Math.floor(interval) + " years";
    }
    interval = seconds / 2592000;
    if (interval > 1) {
        return Math.floor(interval) + " months";
    }
    interval = seconds / 86400;
    if (interval > 1) {
        return Math.floor(interval) + " days";
    }
    interval = seconds / 3600;
    if (interval > 1) {
        return Math.floor(interval) + " hours";
    }
    interval = seconds / 60;
    if (interval > 1) {
        return Math.floor(interval) + " minutes";
    }
    return Math.floor(seconds) + " seconds";
}

function getShortenLink(originalLink) {
    $.ajax('/api/v1/links',
        {
            timeout: 5000,
            type: 'POST',
            data: jQuery.param({ link: originalLink}),

            success: data => {
                if (data === "") {
                    $("#shortenFail").show();
                } else {
                    let alertLink = $(".alert-link");
                    $("#shortenSuccess").show();
                    alertLink.html(data);
                    alertLink.attr("href", data);
                }
                buttonContainer.html(buttonHTML);
            },
            error: () => {
                let alertDanger = $("#shortenFail");
                alertDanger.show();
                alertDanger.html("Something went wrong 🤔");
                buttonContainer.html(buttonHTML);
            }
        });
}

function getHistory() {
    $.ajax('/api/v1/links',
        {
            timeout: 5000,
            type: 'GET',
            dataType: 'json',

            success: data => {
                if (data === "") {
                    showHistoryError();
                } else {
                    $("#history_table").show();
                    let historyTable = $("#history_table_body");
                    historyTable.empty();
                    data.forEach((value, i) => historyTable.append("<tr>\n" +
                        "<th scope=\"row\">"+i+"</th>\n" +
                        "<td>"+value.actualLink+"</td>\n" +
                        "<td>"+ location.protocol + '//' + location.host + "/" + value.shortenLink+"</td>\n" +
                        "<td>"+timeSince(new Date(value.created))+" ago</td>\n" +
                        " </tr>"));

                }
                $("#timer-loader").hide();
            },
            error: () => {
                showHistoryError();
                $("#timer-loader").hide();
            }
        });
}

function showHistoryError() {
    $("#history_table").hide();
    let alertDanger = $("#historyError");
    alertDanger.show();
    alertDanger.html("Something went wrong 🤔");
}

function clearAlerts() {
    $(".alert").hide();
    $("#shortenSuccess").html("Your shorten link: <a class=\"alert-link\">Link</a>");
    $("#shortenFail").html("Invalid URL :(");
}

$(document).ready(() => {
    clearAlerts();
    $("#basic-url").keypress(e => {
        if(e.which === 13) {
            shorten();
        }
    });
    $(".nav a").click(function(e){
        e.preventDefault();

        $(this).tab('show');
        if ($(this).attr("id") === "history_link") {
            $("#history_table").hide();
            $("#historyError").hide();
            $("#timer-loader").show();
            getHistory();
        }
    });
})
