const zone = sessionStorage["zone"];

$(function (){
    $.ajax({
        type: 'POST',
        data: {zoneName : zone},
        dataType: 'json',
        url: "my-orders",
        timeout: 2000,
        error: function() {
            //console.error("Failed to submit");
        },
        success: function(orders) {
            loadOrders(orders);
        },
    });


    return false;
});

function loadOrders(orders) {
    if(orders === null){
        $('.ordersDiv').hide();
    }
    else{
        $.each(orders || [], function(index, order){
            var content1, content2='', content3;

             content1 =`
                <div class="card cardOrder text-white bg-secondary mb-3 " style="max-width: 40rem;">
                    <div class="card-header text-center">
                        <h5 class="card-title">Order #${order.serialNumber}</h5>
                    </div>
                    <br>
                    <p class="card-text text-center">Date: ${order.date}<br>Delivery's address: ${order.location}<br>Number of stores: ${order.numberOfStores}</p>


                    <div class="card-body main" style="max-width: 38rem;">`;



            $.each(order.myItems, function(index, item){
                content2 += `<div class="card cardItem2 text-white bg-secondary mb-3 text-center" style="min-width: 18rem; max-width: 18rem;">
                            <div class="card-header text-center">
                                ${item.productName} - ${item.serialNumber}
                            </div>
                            <div class="card-body">
                                <p class="card-text">Purchase Mode: ${item.purchaseCategory}<br>From: ${item.storeData}<br>Quanity: ${item.quantityChoosen}
                                    <br>Price per unity: ${item.price}<br>Total price: ${item.totalPrice}<br>From discount: ${item.fromDiscount} </p>
                            </div>
                        </div>`;
            });





            content3 =   `</div>
                    <br>
                    <p class="card-text text-center">Number of items: ${order.numberOfItems}<br>Items' price: ${order.totalPriceOfItems}<br>
                    Delivery price: ${order.deliveryCost}<br> Total price: ${order.totalPriceOfOrder}</p>
                    <br>
                </div>`;

            var finalContent = content1 + content2 + content3;

                 $('#myOrdersContainers'). append(finalContent);
        });

        $('.error').hide();
    }
}