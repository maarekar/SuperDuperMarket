$('.stores').owlCarousel({
    loop: false,
    margin:10,
    nav:true,
    items : 4,
});

const zone = sessionStorage["zone"];
var myStores;

$(function (){
    $.ajax({
        type: 'POST',
        data: {zoneName : zone},
        dataType: 'json',
        url: "myStores",
        timeout: 2000,
        error: function() {
            //console.error("Failed to submit");
        },
        success: function(stores) {
            myStores = stores;
            loadStores(stores);
        },
    });


    return false;
});

function loadStores(stores) {
    if(stores === null){
        $('.ordersDiv').hide();
        $('#showFeedbacksButton').attr('disabled','disabled');
    }
    else{
        $('.noStores').hide();



        $.each(stores || [], function(index, store) {
            const content = `
            <div class="card cardItem text-white bg-secondary mb-3 text-center" style="min-width : 18rem;max-width: 18rem;">
                <div class="card-header ">${store.name} - ${store.id}</div>
                <div class="card-body">`;

             const content2 ='</div> </div>';
            var content3;
            if(store.numberOfOrders === 0){
                 content3 =  "There are no orders in this store";
            } else{
                content3 = `<button onclick="showOrdersStore('${store.name}', ${store.id})"> Show Orders</button>`;
            }

            const finalContent = content + content3 + content2;


            $('#myStores').trigger('add.owl.carousel', [finalContent])
                .trigger('refresh.owl.carousel');
        });
    }
}

function showOrdersStore(storeName, storeID){
    $('.ordersOrFeedbacksContainer').empty();
    $("#forTitle").empty();
    $("#forTitle").append('<h3 class="text-center">Orders of ' + storeName + '</h3>');
    $.each(myStores || [], function(index, store) {
        if(store.id === parseInt(storeID)){
            $.each(store.orders || [], function(index, order) {
                const content1 = ` <div class="card cardOrder text-white bg-secondary mb-3 " style="min-width: 33rem; max-width: 33rem;">
                            <div class="card-header text-center">
                                <h5 class="card-title">Order #${order.serialNumber}</h5>
                            </div>
                            <br>
                            <p class="card-text text-center">Date: ${order.date}<br>Delivery's address: ${order.location}</p>


                            <div class="card-body main" style=" min-width: 30rem; max-width: 30rem; margin:0 auto;">`;

                var content2 ='';

                $.each(order.myItems || [], function(index, item) {
                    content2 += `   
                                <div class="card cardItem2 text-white bg-secondary mb-3 text-center" style="min-width: 18rem; max-width: 18rem;">
                                    <div class="card-header text-center">
                                        ${item.productName} - ${item.serialNumber}
                                    </div>
                                    <div class="card-body">
                                        <p class="card-text">Purchase Mode: ${item.purchaseCategory}<br>From: ${item.storeData}<br>Quantity: ${item.quantityChoosen}
                                            <br>Price per unity: ${item.price}<br>Total price: ${item.totalPrice}<br>From discount: ${item.fromDiscount} </p>
                                    </div>
                                </div> `;
                });



                  var content3 =`          </div>
                            <br>
                            <p class="card-text text-center">Number of items: ${order.numberOfItems}<br>Items' price: ${order.totalPriceOfItems}
                            <br>Delivery price: ${order.deliveryCost}<br> Total price: ${order.totalPriceOfOrder}</p>
                            <br>
                        </div>
                `;

                  const finalContent = content1 + content2 + content3;

                  $('.ordersOrFeedbacksContainer').append(finalContent);
            });
        }
    });
}

function showFeedback(){
    $.ajax({
        type: 'POST',
        data: {zoneName : zone},
        dataType: 'json',
        url: "myFeedbacks",
        timeout: 2000,
        error: function() {
            //console.error("Failed to submit");
        },
        success: function(feedbacks) {
            loadFeedbacks(feedbacks);
        },
    });


    return false;
}

function loadFeedbacks(feedbacks){
    $('.ordersOrFeedbacksContainer').empty();
    $("#forTitle").empty();

    if(feedbacks === null){
        var message = "You don't have feedbacks!";
        $("#forTitle").append('<h3 class="text-center">' +message +'</h3>');

    } else{
        $("#forTitle").append('<h3 class="text-center">My Feedbacks</h3>');

        $.each(feedbacks || [], function(index, feedback) {

            const content1=`<div class="card cardItem2 text-white bg-secondary mb-3 " style="min-width: 15rem; " >
                            <div class="card-header text-center">
                                ${feedback.customerName}
                            </div>
                            <div class="card-body" >  <u>Date:</u> ${feedback.date}<br>
                                        <u>Rating:</u> ${feedback.rating}<br><br>
                                        <u>Message:</u><br>   ${feedback.message}
                            </div>
                        </div>`;

            $('.ordersOrFeedbacksContainer').append(content1);
        });
    }
}