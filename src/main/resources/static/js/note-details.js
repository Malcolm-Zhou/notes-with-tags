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