let allTags = $("button.tag");
let noteId = $("#noteId").val();


//删除模式切换class，目的是改变tag颜色
$("#delete-tag-mode").change(function () {
    $.each(allTags, function (index, tag) {
        $(tag).toggleClass("btn-primary");
        $(tag).toggleClass("btn-danger");
    });
});

//删除模式时ajax删除tag
$.each(allTags, function (index, tag) {
    $(tag).click(function () {
        let tagId = $(this).attr("tag-id");
        let inDeleteMode = $(this).hasClass("btn-danger");
        if (inDeleteMode) {
            let url = "/deleteTag?noteId=" + noteId + "&tagId=" + tagId;
            $.ajax({
                url: url,
                method: 'get',
                success: function (result) {
                    console.log(result);
                    $(tag).remove();
                }
            });
        }
    })
});

//自动刷新Markdown预览
$('#content').keyup(function () {
    let content = $(this).val();
    $.ajax({
        url: "/refreshMD",
        method: 'post',
        data: {'content': content},
        success: function (result) {
            $('#mdHTML').html(result);
        }
    });
});

//文本框高度自适应
let textarea = document.getElementById('content');
textarea.style.height = 'auto';
textarea.style.height = textarea.scrollHeight + "px";

textarea.onkeyup = function() {
    this.style.height = 'auto';
    this.style.height = this.scrollHeight + "px";
};