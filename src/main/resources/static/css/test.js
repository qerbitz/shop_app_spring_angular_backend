$('.button').on('click', function (e) {
    $('.hidden').toggle();
});

// Get the modal
var modal = document.getElementById("myModal");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

$(document).ready(function () {
    $('#carouselExampleControls').find('.carousel-item').first().addClass('active');
});

$(document).ready(function () {
    // MDB Lightbox Init
    $(function () {
        $("#mdb-lightbox-ui").load("mdb-addons/mdb-lightbox-ui.html");
    });
});



