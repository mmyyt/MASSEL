let participantBtn = document.querySelector('.participantBtn');
let detailBtn = document.querySelector('.detailBtn');

participantBtn.addEventListener('click', ()=>{
    //console.log("참여자버튼");
    document.querySelector('.detailDiv').classList.add('hidden');
    document.querySelector('.participationForm').classList.remove('hidden');
    
    //밑줄생기게
    participantBtn.classList.add('underline');
    detailBtn.classList.remove('underline');

});

detailBtn.addEventListener('click', ()=>{
    //console.log("디테일버튼");
    document.querySelector('.participationForm').classList.add('hidden');
    document.querySelector('.detailDiv').classList.remove('hidden');

    participantBtn.classList.remove('underline');
    detailBtn.classList.add('underline');
});

