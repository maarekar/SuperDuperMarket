const zone = sessionStorage["zone"];


$('.items').owlCarousel({
    loop: false,
    margin:10,
    nav:true,
    items : 4,
});


$('.stores').owlCarousel({
    loop: false,
    margin:10,
    nav:true,
    items : 2,
});



$(function (){
    $(".zoneName").text(zone);
    if(sessionStorage["type"] === "Owner"){
        notifier();
    }

});

function loadItems(items) {

    $.each(items || [], function(index, item) {
        const content = `
            <div class="card cardItem text-white bg-secondary mb-3 text-center" style="max-width: 18rem;">
                <div class="card-header ">${item.productName} - ${item.serialNumber}</div>
                <div class="card-body">
                    <p class="card-text">Purchase Mode: ${item.purchaseCategory}<br>Sold in ${item.numberOfStores} stores<br>Average price: ${item.averagePrice}<br>
                    Sold ${item.quantitySold} times</p>
                </div>
            </div>
                     `;
        $('#itemsZone').trigger('add.owl.carousel', [content])
            .trigger('refresh.owl.carousel');
    });

}

function loadStore(stores) {

    $.each(stores|| [], function(index, store) {
        //const items = loadItemsOfStore(store);


        const content1 =`
            <div class="card cardStore text-white bg-secondary mb-3 " style=" max-width: 40rem;">
            <div class="card-header text-center">
            <h5 class="card-title">${store.name} - ${store.id},   Owner: ${store.ownerName} </h5>
            </div>
            <p class="card-text text-center">Items in this store: </p>
            <div class="card-body main" style="  max-width: 35rem; margin:0 auto;"> 
        `;

        var content2 = '';

        $.each(store.itemsList || [], function(index, item){
            content2 += `
            <div class="card cardItem2 text-white bg-secondary mb-3 text-center" style=" max-width: 10rem;">
                                <div class="card-body">
                                    <p class="card-text">${item.productName} - ${item.serialNumber}<br>${item.purchaseCategory}<br>Price: ${item.price}<br>Sold ${item.quantitySold}
                                        times</p>
                                </div>
                            </div>
                     `;

        })

        const content3 =`</div>
<p class="card-text text-center">Location: ${store.location}, PPK: ${store.PPK}<br>Number of orders: ${store.numberOfOrders}<br>Price items sold: ${store.priceItemSold}, All deliveries price: ${store.allDeliveries}</p>
<br><br>
</div>
</div>`;
        const finalcontent = content1 + content2 + content3;
        $('#storesZone').trigger('add.owl.carousel', [finalcontent])
            .trigger('refresh.owl.carousel');
    });

}


$(function (){
    $.ajax({
        type: 'POST',
        data: {zoneName : zone},
        dataType: 'json',
        url: "zones-stores",
        timeout: 2000,
        error: function() {
            //console.error("Failed to submit");
        },
        success: function(stores) {
            loadStore(stores);
        },
    });


    return false;
});

$(function (){
    $.ajax({
        type: 'POST',
        data: {zoneName : zone},
        dataType: 'json',
        url: "zones-items",
        timeout: 2000,
        error: function() {
            //console.error("Failed to submit");
        },
        success: function(items) {
            loadItems(items);
        },
     });


    return false;
});

$("#backButton").click(function(){
    window.location.assign("home.html");
});

