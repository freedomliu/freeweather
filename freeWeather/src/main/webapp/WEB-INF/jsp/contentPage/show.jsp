<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
	<div class="row" style="margin-left:150px;margin-right:150px;">

    <div class="col-sm-6 col-md-3">
        <a href="#" class="thumbnail">
            <img src="image/show1.png" onclick="showModal(1)"
                 alt="色斑图样例">
        </a>
		 <div class="caption">
                <h3>色斑图样例</h3>
                <p>数据纯属虚构</p>
                <p>
                    <a href="javascript:void(0)" onclick="showModal(1)" class="btn btn-primary" role="button">
                        查看
                    </a>
                </p>
            </div>
    </div>
    <div class="col-sm-6 col-md-3">
        <a href="#" class="thumbnail">
            <img src="image/show2.png" onclick="showModal(2)"
                 alt="色斑图样例">
        </a>
		 <div class="caption">
                <h3>色斑图样例</h3>
                <p>数据纯属虚构</p>
                <p>
                    <a href="javascript:void(0)" onclick="showModal(2)"  class="btn btn-primary" role="button">
                        查看
                    </a>
                </p>
            </div>
    </div>
    <div class="col-sm-6 col-md-3">
        <a href="#" class="thumbnail">
            <img src="image/show.png"
                 alt="无法加载图片时的说明">
                   </a>
                  <div class="caption">
                <h3>缩略图标签</h3>
                <p>一些示例文本。一些示例文本。</p>
                <p>
                    <a href="#" class="btn btn-primary" role="button">
                        按钮
                    </a>
                    <a href="#" class="btn btn-default" role="button">
                        按钮
                    </a>
                </p>
            </div>
      
    </div>
	<div class="col-sm-6 col-md-3">
        <a href="#" class="thumbnail">
            <img src="image/show.png"
                 alt="无法加载图片时的说明">
        </a>
		 <div class="caption">
                <h3>缩略图标签</h3>
                <p>一些示例文本。一些示例文本。</p>
                <p>
                    <a href="#" class="btn btn-primary" role="button">
                        按钮
                    </a>
                    <a href="#" class="btn btn-default" role="button">
                        按钮
                    </a>
                </p>
            </div>
    </div>
</div>
<div class="row" style="margin-left:150px;margin-right:150px;">

    <div class="col-sm-6 col-md-3">
        <a href="#" class="thumbnail">
            <img src="image/show.png"
                 alt="无法加载图片时的说明">
        </a>
		 <div class="caption">
                <h3>缩略图标签</h3>
                <p>一些示例文本。一些示例文本。</p>
                <p>
                    <a href="#" class="btn btn-primary" role="button">
                        按钮
                    </a>
                    <a href="#" class="btn btn-default" role="button">
                        按钮
                    </a>
                </p>
            </div>
    </div>
    <div class="col-sm-6 col-md-3">
        <a href="#" class="thumbnail">
            <img src="image/show.png"
                 alt="无法加载图片时的说明">
        </a>
         <div class="caption">
                <h3>缩略图标签</h3>
                <p>一些示例文本。一些示例文本。</p>
                <p>
                    <a href="#" class="btn btn-primary" role="button">
                        按钮
                    </a>
                    <a href="#" class="btn btn-default" role="button">
                        按钮
                    </a>
                </p>
            </div>
    </div>
    <div class="col-sm-6 col-md-3">
        <a href="#" class="thumbnail">
            <img src="image/show.png"
                 alt="无法加载图片时的说明">
                   </a>
                  <div class="caption">
                <h3>缩略图标签</h3>
                <p>一些示例文本。一些示例文本。</p>
                <p>
                    <a href="#" class="btn btn-primary" role="button">
                        按钮
                    </a>
                    <a href="#" class="btn btn-default" role="button">
                        按钮
                    </a>
                </p>
            </div>
      
    </div>
	<div class="col-sm-6 col-md-3">
	        <a href="#" class="thumbnail">
	            <img src="image/show.png"
	                 alt="无法加载图片时的说明">
	                 
	        </a>
	         <div class="caption">
                <h3>缩略图标签</h3>
                <p>一些示例文本。一些示例文本。</p>
                <p>
                    <a href="#" class="btn btn-primary" role="button">
                        按钮
                    </a>
                    <a href="#" class="btn btn-default" role="button">
                        按钮
                    </a>
                </p>
            </div>
	</div>
</div>
<div class="modal fade" id="myModal" backdrop="false" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1080;height: auto;">
		<div class="modal-content">
			<div class="modal-body">
				<img id="bigImg"></img>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</body>
</html>
<script>
function showModal(num)
{
	$('#myModal').modal('show');
	$("#bigImg").attr("src","image/show"+num+".png");
}

</script>