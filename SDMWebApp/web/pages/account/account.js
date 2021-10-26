var refreshRate = 2000; //milli seconds
var customer ="Customer";



$(function() {
    putButton();
    transactionsList();
    getNameAndBalance();
    setInterval(transactionsList, refreshRate);

    setInterval(getNameAndBalance, refreshRate);
});

function transactionsList() {
    $.ajax({
        method: "GET",
        url: "account",
        success: function(transactions) {
            refreshTransactionsList(transactions);
        }
    });
}

function refreshTransactionsList(transactions) {

    //clear all current zones
    $("#table-transactions tbody").empty();

    $.each(transactions || [], function(index, transaction) {

        $("#dataTransactions").append('<tr> <td>' + transaction.type
            + '</td>  <td>' + transaction.date
            + '</td> <td>' + transaction.amount
            + '$</td> <td>' + transaction.amountBefore
            + '$</td> <td>' + transaction.amountAfter
            + '$</td></tr>');


    });
}

function getNameAndBalance(){
    $.ajax({
        method: "GET",
        url: "balance",
        success: function(user) {
            $(".username").text(user.name);
            $(".balance").text(user.balance);
        }
    });
}

function putButton(){
    $.ajax({
        method: "GET",
        url: "balance",
        success: function(user) {
            if(user.accountType === customer){
                $('<a href="" class="btn align-middle   ml-auto mr-3 order-lg-last" id="chargeCredit" data-toggle="modal" data-target="#chargeCreditForm">Charge Credit</a>'
                ).appendTo($("#navBarHome"));
            }
        }
    });
}

$(function () {
    $("form.form-charge").submit(function(){
        console.log("in charge submit");
        $.ajax({
            method: "POST",
            data: $(this).serialize(),
            url: this.action,
            timeout: 2000,
            error: function () {
                $(".error").text("Failed to charge - AJAX error.");
            },
            success: function (resp) {
                $(".error").text(resp);
            }
        });
        return false
    })
});

