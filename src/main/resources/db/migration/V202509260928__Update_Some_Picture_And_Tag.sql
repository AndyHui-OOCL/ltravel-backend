INSERT INTO component_images (id, name, travel_component_id, url)
VALUES
    (234,'故宫_1', 2, 'https://raw.githubusercontent.com/AndyHui-OOCL/ltravel-images/main/static/gugong_1.jpg'),
    (235,'颐和园_1', 5, 'https://raw.githubusercontent.com/AndyHui-OOCL/ltravel-images/main/static/yiheyuan_1.jpg'),
    (236,'苏堤_1', 3, 'https://raw.githubusercontent.com/AndyHui-OOCL/ltravel-images/main/static/sudi_1.jpg'),
    (237,'灵隐寺_1', 4, 'https://raw.githubusercontent.com/AndyHui-OOCL/ltravel-images/main/static/lingyinsi_1.jpg');


UPDATE travel_components
SET ticket_url = CASE id
    -- 北京景点
                     WHEN 1 THEN 'https://pc.xiangshantickets.com/index.html#/' -- 香山公园
                     WHEN 2 THEN 'https://ticket.dpm.org.cn/' -- 故宫博物院
                     WHEN 5 THEN 'https://ticket.summerpalace-china.com/#/' -- 颐和园

    -- 桂林景点
                     WHEN 6 THEN 'http://h5c.4009910978.cn/pages/product_detail/detail?id=891&refid=V8RNB4XH2848FN8DRRP22LBJ8' -- 象鼻山
                     WHEN 7 THEN 'https://www.dahepiao.com/jingqulvyou1/20250124503136.html' -- 桂海晴岚
                     WHEN 10 THEN 'https://www.citsguilin.com/menpiao/riyueshuangta.htm' -- 日月双塔
                     WHEN 13 THEN 'https://www.citsguilin.com/menpiao/yinziyan.htm' -- 银子岩
                     WHEN 15 THEN 'https://wx.17u.cn/shipnew/h5/#/Views/Index/Index?refid=' -- 遇龙河竹筏漂流

    -- 怒江景点
                     WHEN 21 THEN 'http://njdxgly.com/templ/index.html' -- 怒江大峡谷

    -- 云南其他景点

    -- 西安景点
                     WHEN 43 THEN 'https://www.sxhm.com/guide.html#guide5' -- 陕西历史博物馆
                     WHEN 44 THEN 'https://xa.bendibao.com/tour/2021520/ly84810.shtm' -- 大雁塔
                     WHEN 46 THEN 'https://mp.s11.cn/newb2c/view/view_26720?order_cust_id=7023242&orderCustId=7023242&parentCustId=965122&userId=' -- 大唐芙蓉园传习活动
                     WHEN 47 THEN 'https://www.dahepiao.com/lvyounews1/20220425268494.html' -- 大唐不夜城
                     WHEN 48 THEN 'https://mp.s11.cn/newb2c/view/xacq?order_cust_id=7023242&orderCustId=7023242&parentCustId=965122&userId=' -- 西安城墙
                     WHEN 49 THEN 'https://m.ctrip.com/webapp/you/gspoi/sight/7/52702.html?seo=0&ishidenavbar=yes&popup=close&autoawaken=close&scene=poiStore' -- 西安钟楼
                     WHEN 50 THEN 'https://m.dahepiao.com/news/20250514513111.html' -- 西安鼓楼鼓乐编钟表演
                     WHEN 53 THEN 'https://bmy.com.cn/index.html' -- 兵马俑
                     WHEN 55 THEN 'https://mp.s11.cn/newb2c/view/view_119946?order_cust_id=7023242&orderCustId=7023242&parentCustId=965122&userId=' -- 华清宫
                     WHEN 57 THEN 'https://xa.bendibao.com/tour/2025313/139101.shtm' -- 长恨歌（实景演出）

    -- 大连景点
                     WHEN 66 THEN 'https://touch.piao.qunar.com/touch/detail_1433498772.html' -- 大连渔人码头
                     WHEN 73 THEN 'https://dl.bendibao.com/news/202185/68089.shtm' -- 大连美术馆
                     WHEN 74 THEN 'https://dl.bendibao.com/traffic/2015929/52595.shtm' -- 201有轨电车（复古线）
                     WHEN 78 THEN 'https://www.lvshunmuseum.cn/order/' -- 旅顺博物馆
                     WHEN 80 THEN 'http://dl.bendibao.com/tour/201514/83705.shtm' -- 白玉山景区
                     ELSE ticket_url -- 不更新其他未指定的id
    END
WHERE id BETWEEN 1 AND 82;


UPDATE component_images
SET url = 'https://raw.githubusercontent.com/AndyHui-OOCL/ltravel-images/main/static/xiangshan_1.jpg'
WHERE id = 1;

UPDATE component_images
SET url = 'https://raw.githubusercontent.com/AndyHui-OOCL/ltravel-images/main/static/xiangshan_2.jpg'
WHERE id = 2;
