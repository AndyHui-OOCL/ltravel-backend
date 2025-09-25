UPDATE travel_components
SET is_location = false
WHERE id IN (11, 12, 15, 20, 22, 46, 50, 69, 70, 74);

UPDATE travel_components
SET is_location = true
WHERE id IN (32);

UPDATE travel_components
SET name = '兴坪古镇非遗DIY', description = '兴坪古镇是一个充满历史文化氛围的古镇，保存了大量明清时期的建筑和街道。在非遗产 DIY 区，游客可以亲手参与扎染、石画、团扇等手作，近距离感受壮锦、漆画等非遗的独特魅力。'
WHERE id = 11;

UPDATE travel_components
SET name = '漓江渔火表演', description = '漓江渔火表演是桂林的一大特色，夜幕降临时，渔夫头戴斗笠、身披蓑衣，在竹筏上点起渔火，训练有素的鸬鹚在水中捕鱼，渔火与山水夜景相互映衬，氛围神秘美妙。游客可以乘坐竹筏，近距离欣赏这场独特的水上表演，感受渔民的生活和文化。'
WHERE id = 12;

UPDATE travel_components
SET name = '遇龙河竹筏漂流'
WHERE id = 15;

UPDATE travel_components
SET name = '大唐芙蓉园传习活动', description = '每日上午 10 时，紫云楼有《东仓鼓乐》传习公益活动，非遗传承人现场教学；仕女馆花神学院提供十二花神主题妆造，芙蓉妆造学院传授传统妆容技法，大唐礼仪体验中心可学唐代礼仪，非遗手作空间能制作鎏金发簪；唐诗峡有 “天生我材必有用” 朗诵会，游客可与 “诗人王维” 对诗联句。'
WHERE id = 46;

UPDATE travel_components
SET name = '西安鼓楼鼓乐编钟表演', description = '西安鼓楼建于明代洪武十三年，与钟楼并称“姊妹楼”，是中国现存最大的鼓楼，楼内陈列陶鼓、鼍鼓、战鼓等数十种古今鼓器，每日 18:00 击二十四节气鼓报暮，12:00-14:00、17:00-19:00 有鼓乐编钟表演，演奏《战鼓风云》《迎宾锣鼓》等曲目。'
WHERE id = 50;

UPDATE travel_components
SET name = '东港音乐喷泉', description = '东港音乐喷泉位于大连东港商务区核心位置，是亚洲最大的音乐喷泉之一。喷泉结合灯光、音乐和水舞表演，创造出壮观的视觉盛宴。在夏季每晚的20:00-20:20（冬季暂停）定时上演的喷泉秀吸引了大量游客和市民前来观赏，成为大连夜生活的一大亮点。'
WHERE id = 69;

UPDATE travel_components
SET name = '威尼斯水城主题花车巡游', description = '大连威尼斯水城以意大利威尼斯为蓝本建造，内有11条运河、200余座欧式建筑，可乘坐贡多拉游船穿梭运河，感受“东方威尼斯”风情。曾举办首届奇幻夏日・水城幻戏奇遇巡游 —— 威尼斯戏剧狂欢盛典，活动持续多日，每日有主题花车巡游，如 “古典权杖”“人鱼悲欢” 等主题花车，还有木偶微戏剧上演，演员与观众全程互动，且 0 元入场。'
WHERE id = 70;