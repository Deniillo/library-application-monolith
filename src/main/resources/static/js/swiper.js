document.addEventListener('DOMContentLoaded', function () {
    const swipersNews = document.querySelectorAll('.swiper-container-news');
    const swipersBooks = document.querySelectorAll('.swiper-container-books');

    swipersNews.forEach((swiperContainer) => {
        const swiper = new Swiper(swiperContainer, {
            loop: true,
            spaceBetween: 10,
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },
            pagination: {
                el: '.swiper-pagination-news',
                clickable: true,
            },
            slidesPerView: 1,
        });

        const images = swiperContainer.querySelectorAll('.swiper-image');
        images.forEach((image) => {
            image.addEventListener('click', (event) => {
                const rect = image.getBoundingClientRect();
                const clickX = event.clientX - rect.left;
                const halfWidth = rect.width / 2;

                if (clickX < halfWidth) {
                    swiper.slidePrev();
                } else {
                    swiper.slideNext();
                }
            });
        });
    });

    swipersBooks.forEach((swiperContainer) => {
        new Swiper(swiperContainer, {
            loop: true,
            pagination: {
                el: swiperContainer.querySelector('.swiper-pagination-books'),
                clickable: true,
            },
            autoplay: {
                delay: 2000,
            },
            effect: 'fade',
            speed: 1200,
        });
    });
});
