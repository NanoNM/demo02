$(function () {
    doResize();
})
$(window).resize(function(){
    doResize();
});

function doResize() {
    if (window.innerWidth<1235){
        $(".Cooldog_container").css({"top":"0px"});
        $(".Cooldog_container").css({"margin-top":"0px"});
        $(".Cooldog_container").css({"width":window.innerWidth});
        $(".Cooldog_container").removeAttr("id");
    }else if(window.innerWidth>1235){
        $(".Cooldog_container").removeAttr("style");
        $(".Cooldog_container").attr("id","befulsize");
    }
}