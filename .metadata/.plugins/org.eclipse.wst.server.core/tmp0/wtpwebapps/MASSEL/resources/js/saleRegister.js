document.getElementById('thumbnailArea').addEventListener('click', function(){
    document.getElementById('thumbnailFile').click();
});

const regExpImg = new RegExp("\.(jpg|jpeg|png|gif|bmp)$", "i"); // 이미지 파일, i = 대소문자 구분 X

//썸네일 이미지 불러오기
function showThumbnailImg(event){
    const thumbnailArea = document.getElementById('thumbnailArea');

    //기존 이미지 비우기
    thumbnailArea.innerHTML = '';
    const file = event.target.files[0];

    //파일이 없으면 종료(파일이 선택되지 않았을경우 종료)
    if(!file){
        thumbnailArea.innerHTML = '썸네일 이미지를 등록해주세요.';
        return;
    }
    //이미지 파일만 업로드할 수 있게
    if(!regExpImg.test(file.name)){
        alert("이미지 파일만 업로드할 수 있습니다.");
        event.target.value = "";
        thumbnailArea.innerHTML = '썸네일 이미지를 등록해주세요.';
        return;
    }
    
    const reader = new FileReader();
    reader.onload = function(event){
        const img = document.createElement("img");
        img.setAttribute("src", event.target.result);
        img.style.width = "100%";
        img.style.height = "100%";
        img.style.objectFit = "cover"; 
        thumbnailArea.appendChild(img);
    };
    if(file){
        reader.readAsDataURL(file);
    }
}

document.querySelector('.saleProductArea').addEventListener('click', function(event) {
    // 이벤트가 발생한 요소가 .productImg 클래스인지 확인
    if (event.target.closest('.productImg')) {
        const wrapper = event.target.closest('.productImgWrapper');
        if (wrapper) {
            const productImgFile = wrapper.querySelector('.productImgFile');
            if (productImgFile) {
                productImgFile.click();
            }
        }
    }else if(event.target.closest('.productDelBtn')){
        event.preventDefault();
        //console.log("상품삭제버튼");
        //삭제버튼이면 box개수 확인하고 삭제
        let productBoxes = document.querySelectorAll('.saleProductBox');

        if(productBoxes.length>1){
           let productBox = event.target.closest('.saleProductBox');
           productBox.remove();
        }else {
            alert("상품은 한개 이상이어야 합니다.");
        }

    }
});

//상품 이미지 불러오기
function showProductImg(event){

    //target의 부모요소 찾기
    const wrapper = event.target.closest('.productImgWrapper');
    if(wrapper){
        //부모요소 안에 productImg 찾기
        const productImg = wrapper.querySelector('.productImg');

        productImg.innerHTML = '';
        const file = event.target.files[0];

        if(!regExpImg.test(file.name)){
            alert("이미지 파일만 업로드할 수 있습니다.");
            event.target.value = "";
            productImg.innerHTML = '<i class="fa-regular fa-image"></i>';
            return;
        }

        const reader = new FileReader();
        reader.onload = function(event){
            const img = document.createElement("img");
            img.setAttribute("src", event.target.result);
            img.style.width = "100%";
            img.style.height = "100%";
            img.style.objectFit = "cover";
            img.style.borderRadius = "8px";
            productImg.appendChild(img);
        };

        if(file){
            reader.readAsDataURL(file);
        }
    }


}

//상품 추가
let addBtn = document.getElementById('productAddBtn');

addBtn.addEventListener('click', (event)=>{
    event.preventDefault();

    let productArea = document.querySelector('.saleProductArea');
    let productBox = document.querySelector('.saleProductBox');

    let newProductBox = productBox.cloneNode(true);

    let pImg = newProductBox.querySelector('.productImg img');
    if(pImg !== null){
        //만약 첫번째 box에서 이미지를 넣었으면
        pImg.removeAttribute('src');
    }else{
        //첫번째 box에서 이미지를 안넣었으면 src null이라고 못알아들음
        //그럼 아이콘도 같이 넣어주기
    }


    newProductBox.querySelector('.pNameInput').value = '';
    newProductBox.querySelector('.pPriceInput').value = '';
    newProductBox.querySelector('.pStockInput').value = '';
    newProductBox.querySelector('.pLimitInput').value = '';

    productArea.appendChild(newProductBox);


})

let productList = [];

function sendProductList() {
    //alert("sendProductList 함수 실행");

    // FormData 선언
    let formData = new FormData();

    // productList 생성
    let productList = [];
    document.querySelectorAll('.saleProductBox').forEach((box, index) => {
        let product = {
            spname: box.querySelector('.pNameInput').value,
            spprice: box.querySelector('.pPriceInput').value,
            stock: box.querySelector('.pStockInput').value,
            maxOrderQuantity: box.querySelector('.pLimitInput').value
        };
        productList.push(product);

        // 이미지 파일 추가
        let imgFileInput = box.querySelector('.productImgFile');
        if (imgFileInput && imgFileInput.files.length > 0) {
            formData.append("imageFiles", imgFileInput.files[0]);
        }
    });

    // productList를 FormData에 추가
    formData.append("productList", new Blob([JSON.stringify(productList)], { type: "application/json" }));

    // AJAX 요청
    try {
        $.ajax({
            url: "/salePath/productListRegister",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                //console.log('서버 응답:', response);
            },
            error: function (error) {
                console.error('에러 발생:', error);
            }
        });
    } catch (error) {
        console.error('에러 내용:', error);
    }
}


//summernote
$(document).ready(function() {
	$('#summernote').summernote({
		  lang: "ko-KR",  		//한글
		  height: 500,    		// 에디터 높이 설정
		  minHeight: null,      // 최소 높이
		  maxHeight: null,      // 최대 높이
		  //focus: true,          // 에디터 로딩후 포커스를 맞출지 여부
		  //placeholder: '작성된 내용이 부적절하고 판단될 시 무통보 삭제 될 수 있습니다.',	//placeholder 설정
          //콜백함수
          callbacks : {
        	  onImageUpload : function (files, editor, welEditable){
        		  for(var i = files.length - 1; i>=0; i--){
        			  imageUpload(files[i], this);
        		  }
        	  }
          }
	});
});


function imageUpload(file, el){
    var formData = new FormData();
    formData.append('file', file);

$.ajax({
    data: formData,
    type: "POST",
    dataType: "JSON",
    url: '/salePath/summernote',
    contentType: false,
    processData: false,
    enctype: 'multipart/form-data',
    success: function(data) {
        // console.log(data);
        // console.log(data.url);
        // console.log(data.responseCode);
        if (data.responseCode === 'success') {
            $(el).summernote('insertImage', data.url);
        } else {
            console.log('이미지 업로드 실패 : ', data);
        }
    },
    error: function(jqXHR, textStatus, errorThrown) {
        console.error("AJAX 요청 실패: " + textStatus + ", " + errorThrown);
        console.log(jqXHR.responseText);
    }
});
}

//기간설정부분

$(function(){
    $("#startDay").datepicker({
        dateFormat : "yy-mm-dd",
        minDate : 0, //오늘부터 선택 가능
        //maxDate : "+3M", //오늘부터 3달까지만 선택 가능
        showAnim : "fadeIn", //선택될 때 애니메이션 효과
        //changeMonth : true, //드롭다운으로 선택 가능하게
        //changeYear : true,
        yearSuffix : "년",
        monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'], // 달력의 월
        monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], // 달력의 월
        dayNamesMin : ['일','월','화','수','목','금','토'], // 달력의 요일 
        dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] // 달력의 요일
      });
});

$(function(){
  $("#endDay").datepicker({
      dateFormat : "yy-mm-dd",
      minDate : 0, //오늘부터 선택 가능
      maxDate : "+3M", //오늘부터 3달까지만 선택 가능
      showAnim : "fadeIn", //선택될 때 애니메이션 효과
      //changeMonth : true, //드롭다운으로 선택 가능하게
      //changeYear : true,
      yearSuffix : "년",
      monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'], // 달력의 월
      monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], // 달력의 월
      dayNamesMin : ['일','월','화','수','목','금','토'], // 달력의 요일 
      dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] // 달력의 요일
  });
});

//startHour

const startHourSelect = document.getElementById("startHour");
const endHourSelect = document.getElementById("endHour");

for(let i=0; i<24; i++){
  //시간 두자리로 하기 위해 문자 채우기
  //적용할문자열.(채울길이, 채울문자)
  //padStart는 앞부분, padEnd는 뒤를 채움
  const hour = String(i).padStart(2, '0');
  const startOption = document.createElement("option");
  startOption.value = hour;
  //option.textContent = hour+"시";
  startOption.textContent = hour;
  startHourSelect.appendChild(startOption);

  const endOption = document.createElement("option");
  endOption.value = hour;
  //option.textContent = hour+"시";
  endOption.textContent = hour;
  endHourSelect.appendChild(endOption);
}

const startMinuteSelect = document.getElementById("startMinute");
const endMinuteSelect = document.getElementById("endMinute");

for(let i=0; i<60; i++){
  const minute = String(i).padStart(2, '0');
  const startOption = document.createElement("option");
  startOption.value = minute;
  startOption.textContent = minute;
  startMinuteSelect.appendChild(startOption);

  const endOption = document.createElement("option");
  endOption.value = minute;
  endOption.textContent = minute;
  endMinuteSelect.appendChild(endOption);
}

//배송방법 추가

let methodAddBtn = document.getElementById("shippingMethodAddBtn");

methodAddBtn.addEventListener("click",(event)=>{
    event.preventDefault();
    let shippingMethodDiv = document.querySelector('.shippingMethodDiv');
    let shippingMethodBox = document.querySelector('.shippingMethodBox');

    let newShippingMethodBox = shippingMethodBox.cloneNode(true);

    newShippingMethodBox.querySelector('.shippingMethodInput').value = '';
    newShippingMethodBox.querySelector('.shippingCostInput').value = '0';

    shippingMethodDiv.appendChild(newShippingMethodBox);

})

document.addEventListener('click', (event)=>{
    if(event.target.classList.contains('shippingDeleteBtn')){
        //console.log("버튼눌림!");
        event.preventDefault();

        //현재 남아있는 shippingMethodBox 개수 확인하기
        let shippingBoxes = document.querySelectorAll('.shippingMethodBox');

        if(shippingBoxes.length>1){
            let shippingBox = event.target.closest('.shippingMethodBox');
            shippingBox.remove();
        }else{
            alert('배송 방법은 한개 이상이어야 합니다.');
        }
    }
})

//배송예정 날짜

$(function(){
    $("#shippingExdate").datepicker({
        dateFormat : "yy-mm-dd",
        minDate : 0, //오늘부터 선택 가능
        showAnim : "fadeIn",
        yearSuffix : "년",
        monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'], // 달력의 월
        monthNames : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], // 달력의 월
        dayNamesMin : ['일','월','화','수','목','금','토'], // 달력의 요일 
        dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] // 달력의 요일
      });
});


//textarea 스크롤바X, 길이 자동늘어나게
function autoResizeTextarea(textarea){
    textarea.style.height = 'auto';
    textarea.style.height = (textarea.scrollHeight) + 'px';
}

let textarea0 = document.getElementById("shippingInstructions");
let textarea1 = document.getElementById("refundMessage");
textarea0.addEventListener('input', function(){
    autoResizeTextarea(this);
})
textarea1.addEventListener('input', function(){
    autoResizeTextarea(this);
})


//택배방법 전송
function sendShippingMethod(){

    //shippingMethod list생성
    let shippingMethod = [];

    //forEach로 shippingBox순회하기
    let shippingMethodBoxes = document.querySelectorAll('.shippingMethodBox');

    shippingMethodBoxes.forEach(box=>{
        let nameValue = box.querySelector(".shippingMethodInput").value;
        let priceValue = box.querySelector(".shippingCostInput").value;

        shippingMethod.push({ shippingMethod:nameValue, shippingCost:priceValue});
    })


    $.ajax({
       url : "/salePath/shippingMethod",
       type : "post",
       data : JSON.stringify(shippingMethod),
       contentType : "application/json; charset=utf-8",
       success : function(response){

       },
       error : function(error){
        console.log("에러발생");
       }
    })
}

//계좌정보전송
function sendAccountInfo(){
    let bankName = document.getElementById("bankName").value;
    let accountNumber = document.getElementById("accountNumber").value;
    let accountHolder = document.getElementById("accountHolder").value;

    $.ajax({
        url : "/salePath/accountInfo",
        type : "post",
        contentType : "application/json; charset=utf-8",
        data : JSON.stringify({
            swriter : sessionId,
            bankName : bankName,
            accountNumber : accountNumber,
            accountHolder : accountHolder
        }),
        success : function(response){

        },
        error : function(error){
            alert("계좌 등록 에러");
        }
    })
}

let submitBtn = document.getElementById('submitBtn');

function validateForm(){
    let thumbnailFile = document.getElementById('thumbnailFile');
    if(!thumbnailFile.files || thumbnailFile.files.length === 0){
        alert("썸네일 이미지를 등록해주세요.");
        return false;
    }
    return true;

}

submitBtn.addEventListener('click',(event)=>{
    if(!validateForm()){
        event.preventDefault();
    }else{
        sendProductList();
        sendShippingMethod();
        sendAccountInfo();
    }
})


let accountNumber = document.getElementById("accountNumber");

function number(event) {
    const pattern = /[^0-9]/g;

    if (pattern.test(event.target.value)) {
        alert("숫자만 입력해주세요.");
        event.target.value = event.target.value.replace(pattern, ''); // 숫자가 아닌 문자를 제거
    }
}
accountNumber.addEventListener('keyup', number);

let saleProductArea = document.querySelector('.saleProductArea');
saleProductArea.addEventListener('keyup', inputNumber);

function inputNumber(event) {
    const pattern = /[^0-9]/g;

    if(event.target.classList.contains('pPriceInput') ||
    event.target.classList.contains('pStockInput')||
    event.target.classList.contains('pLimitInput')){
        if (pattern.test(event.target.value)) {
            alert("숫자만 입력해주세요.");
            event.target.value = event.target.value.replace(pattern, ''); // 숫자가 아닌 문자를 제거
        }
    }
}

let shippingMethod = document.querySelector('.shippingMethod');
shippingMethod.addEventListener('keyup', (event)=>{
    console.log(event.target);
    const pattern = /[^0-9]/g;
    if(event.target.classList.contains('shippingCostInput')){
        if (pattern.test(event.target.value)) {
            alert("숫자만 입력해주세요.");
            event.target.value = event.target.value.replace(pattern, ''); // 숫자가 아닌 문자를 제거
        }
    }
})