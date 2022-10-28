package com.jojo.recovery.utils;

import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * 分装html的分页类
 */
public class PageUtil {
    public static String fmtdate(long times){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(times);
    }

    public static String fmtdate(long times,String type){
        if (type == null || type == ""){
            type = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        return simpleDateFormat.format(times);
    }

    public static String searchParams(HttpServletRequest request) {
        String queryString = "";
        for (String key : request.getParameterMap().keySet()) {
            if (key.equals("page") || key.equals("sortType")) {
                continue;
            }
            String[] values = request.getParameterMap().get(key);
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                queryString += key + "=" + value + "&";
            }
        }
// 去掉最后一个空格
        if (null != queryString && !"".equals(queryString)){
            queryString = queryString.substring(0, queryString.length() - 1);
        }
        request.setAttribute("searchParams", queryString);
        return queryString;
    }

    /**
     * 生成链接URL
     * @param  page 页码
     * @return string
     */
    private static String url(int page, HttpServletRequest request){
        String getUri = request.getRequestURI();//获取pathinfo
        String getParam = request.getQueryString();//获取？后面参数
        String newParam = "";
        if(getParam != null){
            //获取参数后，取掉分页的page=2参数重新返回
            newParam = getParam.replaceAll("page=(\\d*)", "");
            if(!(newParam.indexOf("&") != -1)){
                newParam = "&" + newParam;
            }
        }
        page = page <= 0 ? 1 : page;
        String pageurl = getUri+"?page=" + page + newParam;
        return pageurl;
    }

    public static String show(PageInfo pageInfo, HttpServletRequest request){

        //第一页
        String the_first = "<li>\n" +
                "                <a href=\""+PageUtil.url(1,request)+"\">首页</a>\n" +
                "            </li>";

        //上一页
        String up_page_class = "";
        if(!pageInfo.isHasPreviousPage()){ up_page_class = "disabled"; }
        String up_page = "<li class=\""+up_page_class+"\">\n" +
                "                <a href=\""+PageUtil.url(pageInfo.getPrePage(),request)+"\" aria-label=\"Previous\">\n" +
                "                    <span aria-hidden=\"true\">«</span>\n" +
                "                </a>\n" +
                "            </li>";

        //下一页
        String down_row_class = "";
        if(!pageInfo.isHasNextPage()){ down_row_class = "disabled"; }
        String down_row = "<li class=\""+down_row_class+"\">\n" +
                "                <a href=\""+PageUtil.url(pageInfo.getNextPage(),request)+"\" aria-label=\"Next\">\n" +
                "                    <span aria-hidden=\"true\">»</span>\n" +
                "                </a>\n" +
                "            </li>";

        //最后一页
        String the_end = "<li>\n" +
                "                <a href=\""+PageUtil.url(pageInfo.getPages(),request)+"\"> 共 "+pageInfo.getPages()+" 页</a>\n" +
                "            </li>";

        //数字连接
        String link_page = "";
        for(int nav : pageInfo.getNavigatepageNums()){
            String link_page_class = "";
            if(nav == pageInfo.getPageNum()){link_page_class = "active";}
            link_page += "<li class=\""+link_page_class+"\"><a href=\""+PageUtil.url(nav,request)+"\">"+nav+"</a></li>";
        }

        String pageStr = "<ul class=\"pagination\">" +
                the_first +
                up_page +
                link_page +
                down_row +
                the_end +
                "</ul>";
        return pageStr;
    }
}

/*只使用html如下：*/
/*<ul class="pagination">
	<li>
		<a th:href="'?page=1'">首页</a>
	</li>
	<li th:class="${pageInfo.hasPreviousPage}? '' : 'disabled'">
		<a th:href="'?page='+${pageInfo.prePage}" aria-label="Previous">
			<span aria-hidden="true">«</span>
		</a>
	</li>

	<li th:each="nav : ${pageInfo.navigatepageNums}" th:class="${nav != pageInfo.pageNum}? '' : 'active'">
		<a th:href="'?page='+${nav}" th:text="${nav}" th:if="${nav == pageInfo.pageNum}">
			<span class="sr-only">(current)</span>
		</a>
		<a th:href="'?page='+${nav}" th:text="${nav}" th:if="${nav != pageInfo.pageNum}"></a>
	</li>

	<li th:class="${pageInfo.hasNextPage}? '' : 'disabled'">
		<a th:href="'?page='+${pageInfo.nextPage}" aria-label="Next">
			<span aria-hidden="true">»</span>
		</a>
	</li>
	<li>
		<a th:href="'?page='+${pageInfo.pages}" th:text="'共 '+${pageInfo.pages}+' 页'"></a>
	</li>
</ul>*/