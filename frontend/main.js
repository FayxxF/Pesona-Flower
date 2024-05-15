// FAQ
window.addEventListener('scroll', function() {
    var navbar = document.getElementById('navbar');
    if (window.scrollY > 0) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
});

const faqs = document.querySelectorAll(".faq");

faqs.forEach((faq) => {
    faq.addEventListener("click", () => {
        faq.classList.toggle("active");
    });
});

// Mawar
function change1() {
    document.getElementById('pic').src='/frontend/images/wAster.jpg';
}
function change2() {
    document.getElementById('pic').src='/frontend/images/bAster.jpg';
}

// Krisan
function kchange1() {
    document.getElementById('pic2').src='/frontend/images/wKrisan.jpg';
}
function kchange2() {
    document.getElementById('pic2').src='/frontend/images/pKrisan.jpg';
}