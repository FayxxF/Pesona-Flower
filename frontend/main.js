// Navbar
window.addEventListener('scroll', function() {
    var navbar = document.getElementById('navbar');
    if (window.scrollY > 0) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
});

// Login Logo
window.addEventListener('scroll', function() {
    const adminLogoPaths = document.querySelectorAll('#adminLogo .adminLogo');
    
    if (window.scrollY > 0) {
        adminLogoPaths.forEach(path => {
            path.setAttribute('fill', 'black');
        });
    } else {
        adminLogoPaths.forEach(path => {
            path.setAttribute('fill', 'white');
        });
    }
});


// FAQ
const faqs = document.querySelectorAll(".faq");

faqs.forEach((faq) => {
    faq.addEventListener("click", () => {
        faq.classList.toggle("active");
    });
});

// Mawar
function change1() {
    document.getElementById('pic').src='/images/wAster.jpg';
}
function change2() {
    document.getElementById('pic').src='/images/bAster.jpg';
}

// Krisan
function kchange1() {
    document.getElementById('pic2').src='/images/wKrisan.jpg';
}
function kchange2() {
    document.getElementById('pic2').src='/images/pKrisan.jpg';
}