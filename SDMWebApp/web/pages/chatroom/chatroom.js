var refreshRate = 2000; //milli seconds
var chatVersion = 0;

//call the server and get the chat version
//we also send it the current chat version so in case there was a change
//in the chat content, we will get the new string as well
function ajaxChatContent() {
    $.ajax({
        url: "chat",
        data: "chatversion=" + chatVersion,
        dataType: 'json',
        success: function(data) {
            if (data.version !== chatVersion) {
                chatVersion = data.version;
                appendToChatArea(data.entries);
            }
            triggerAjaxChatContent();
        },
        error: function(error) {
            triggerAjaxChatContent();
        }
    });
}
function triggerAjaxChatContent() {
    setTimeout(ajaxChatContent, refreshRate);
}

$(function() {
    ajaxChatContent();
    //The chat content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxChatContent();
});

function createChatEntry (entry){
    //entry.chatString = entry.chatString.replace (":)", "<img class='smiley-image' src='../../common/images/smiley.png'/>");
    return $("<span class=\"success\">").append(entry.username + "> " + entry.chatString);
}

function appendChatEntry(index, entry){

    const container = document.getElementById('chatarea');
    const card = document.createElement('div');
    card.classList = 'card-body';
    // Construct card content
    const content = `
<div class="card shadow p-3 mb-3 bg-white rounded" >
    <div class="card-body bodyCard2 ">
                <h5 class="card-title  titleCard text-center">${entry.username}</h5>
<!--                <h6 class="card-subtitle text-muted">Message:</h6>-->
                <p>${entry.chatString}</p>
            </div>
            </div>
  `;

    // Append newly created card element to the container
    container.innerHTML += content;
    $("#chatarea").append("<br>");
}

//entries = the added chat strings represented as a single string
function appendToChatArea(entries) {
//    $("#chatarea").children(".success").removeClass("success");

    // add the relevant entries
    $.each(entries || [], appendChatEntry);

    // handle the scroller to auto scroll to the end of the chat area
    var scroller = $("#chatarea");
    var height = scroller[0].scrollHeight - $(scroller).height();
    $(scroller).stop().animate({ scrollTop: height }, "slow");

    // $('html, body').animate({scrollTop:$(document).height()}, 'slow');

}



//add a method to the button in order to make that form use AJAX
//and not actually submit the form
$(function() { // onload...do
    //add a function to the submit event
    $("#chatform").submit(function() {
        $.ajax({
            data: $(this).serialize(),
            url: this.action,
            timeout: 2000,
            error: function() {
                //console.error("Failed to submit");
            },
            success: function(r) {
                //do not add the user string to the chat area
                //since it's going to be retrieved from the server
                //$("#result h1").text(r);
                ajaxChatContent();
            }
        });

        $("#userstring").val("");
        // by default - we'll always return false so it doesn't redirect the user.
        return false;
    });
});