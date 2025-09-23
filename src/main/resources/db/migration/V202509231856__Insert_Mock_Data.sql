-- travel_plan
INSERT INTO ltravel_db.travel_plan (id, city_name, description, is_local_travel, is_popular, title)
VALUES (1, '北京',
        '秋天的北京，气候凉爽宜人，香山公园的红叶层林尽染，色彩斑斓，是摄影和赏枫的绝佳去处。除此之外，故宫博物院展示了丰富的明清皇家文化，游客可以深入了解中国古代宫廷生活。颐和园的皇家园林风光与昆明湖的水面倒影相映成趣，适合悠闲漫步和文化探访。此行程兼顾自然美景与历史文化，适合文化爱好者和家庭游客。',
        TRUE, TRUE, '北京秋季赏枫之旅'),

       (2, '杭州',
        '杭州的秋季西湖景色迷人，湖面平静如镜，苏堤两岸银杏叶渐黄，秋风拂面，令人心旷神怡。灵隐寺古朴庄严，香火鼎盛，是感受佛教文化和自然静谧的好去处。此行程适合喜欢休闲漫步、摄影及文化体验的游客，带来身心放松的完美假期。秋季气候温和，适合户外活动和品味江南风情。',
        TRUE, TRUE, '杭州秋日西湖漫步');


-- travel_component
INSERT INTO ltravel_db.travel_components (address, current_occupation, description, future_occupation, location, name,
                                          open_time, rating, suggestion_play_time, way_of_commute)
VALUES ('北京市海淀区香山公园', 100,
        '香山公园素以秋季红叶闻名，山坡遍布枫树与槭树，金黄与火红交织成一片壮丽的色彩画卷。园内步道完善，有观景台和古树群，适合拍照、徒步和野餐。每年红叶季大量摄影爱好者与游客前来，建议选择平日清晨或傍晚避开高峰并携带保暖衣物。',
        1000, b'0', '香山公园', '06:00-18:00', 4.7, 120, '步行/公交'),
       ('北京市故宫东华门', 100,
        '故宫博物院是明清两代的皇家宫殿，建筑规模宏大、文物藏品丰富。进入午门后可见太和殿、中和殿、保和殿等主要殿宇，长时间参观可深度感受皇家礼制与古代宫廷建筑艺术。馆内常设与临时展览众多，建议预留充足时间并提前在线购票以缩短排队。',
        1000, b'0', '故宫博物院', '08:30-17:00', 4.8, 180, '地铁/步行'),
       ('杭州市西湖苏堤', 100,
        '苏堤横贯西湖，为历代名人所述的经典景致之一。春有花、夏有绿、秋有黄叶、冬有雪意，四季各具风情。漫步苏堤可观赏湖面波光、远山剪影与湖心小岛；傍晚时分落日与湖面相映，适合散步、摄影与租船泛舟。沿线茶座与休憩处众多，适合慢节奏游览。',
        1000, b'1', '苏堤', '全天开放', 4.6, 90, '步行/自行车'),
       ('杭州市灵隐寺', 100,
        '灵隐寺为杭州著名古刹，始建年代久远，香火旺盛，寺内殿宇庄严，佛像与壁画具有较高的文物价值。寺旁环山、瀑布与林木环绕，环境宁静肃穆，是修心与拍摄文化景观的好去处。节假日游客较多，建议清晨或工作日参访以避开人潮，并尊重寺内礼仪。',
        1000, b'0', '灵隐寺', '07:00-17:30', 4.5, 120, '公交/步行'),
       ('北京市颐和园', 100,
        '颐和园为保存完好的皇家园林典范，昆明湖、万寿山与长廊、佛香阁等建筑互相辉映。乘船可近距离观赏湖面与远眺宫苑景致；长廊以彩绘著称，讲述众多历史与传说。春秋两季景色尤为迷人，适合半日或一天深度游览并结合博物馆与古建筑讲解。',
        1000, b'1', '颐和园', '07:00-18:00', 4.7, 150, '地铁/公交');

-- component
INSERT INTO ltravel_db.component_images (name, travel_component_id, url)
VALUES ('香山红叶', 1, 'https://raw.githubusercontent.com/AndyHui-OOCL/ltravel-images/main/static/beijing_2.jpg'),
       ('香山观景', 1, 'https://raw.githubusercontent.com/AndyHui-OOCL/ltravel-images/main/static/beijing_2.jpg');

-- plan_images
INSERT INTO ltravel_db.plan_images (name, travel_plan_id, url)
VALUES ('beijing_view2', 1, 'https://raw.githubusercontent.com/AndyHui-OOCL/ltravel-images/main/static/beijing_2.jpg'),
       ('hangzhou_view2', 2,
        'https://raw.githubusercontent.com/AndyHui-OOCL/ltravel-images/main/static/hangzhou_2.jpg');

-- travel_days
INSERT INTO ltravel_db.travel_days (component_order, day_num, travel_component_id, travel_plan_id)
VALUES (1, 1, 1, 1), -- 北京 第一天：香山
       (2, 1, 2, 1), -- 北京 第一天：故宫
       (1, 1, 3, 2), -- 杭州 第一天：苏堤
       (2, 1, 4, 2), -- 杭州 第一天：灵隐寺
       (1, 2, 5, 1);
-- 北京 第二天：颐和园

-- users
INSERT INTO ltravel_db.users (id, password, user_name)
VALUES (1,'password123', 'alice'),
       (2, 'securepwd', 'bob'),
       (3, 'traveller2025', 'charlie');

-- comments
INSERT INTO ltravel_db.comment (description, is_like, travel_component_id, user_id)
VALUES ('香山的红叶太美了，拍了很多照片！', b'1', 1, 1),
       ('故宫人很多，但历史气息很浓，值得一看。', b'1', 2, 2),
       ('苏堤傍晚散步非常舒服，推荐租个自行车环湖。', b'1', 3, 3),
       ('灵隐寺香火旺盛，建议早上前往避开人流。', b'0', 4, 1),
       ('颐和园景色优美，长廊很适合拍照。', b'1', 5, 2);

-- official_comments
INSERT INTO ltravel_db.official_comment (event_comment, overall_comment, promote_reason, rating, travel_plan_id)
VALUES ('秋季为北京观赏红叶的最佳时节，尤其是香山一带景色烂漫。赏枫期间人流会集中，部分热门观景点会出现排队或拥堵现象，建议选择清晨或工作日出行，并预留足够的步行时间。若想兼顾故宫、颐和园等文化景点，建议合理安排行程并提前线上购票以节省排队时间。天气早晚温差较大，请备好防寒衣物与舒适的步行鞋。',
        '本路线将自然景观与历史文化有机结合：香山的红叶提供视觉盛宴，故宫与颐和园则让人深度体验北京的皇家文化与园林艺术。整体行程节奏适中，既能满足摄影和观光需求，也适合家庭和文化爱好者。若按建议错峰出行，可获得更好的体验与较少的人群干扰。',
        '推荐理由：每年金秋红叶季为本地与外地游客的重要出行时段，香山景致独特且极具摄影价值；同时加入故宫与颐和园等文化景点，使得行程兼具自然与人文，适合希望一次游览多重体验的游客。',
        4.6, 1),

       ('西湖在秋季呈现柔和的金黄与清澈的湖光，尤其苏堤两侧的银杏林与湖面倒影相得益彰，适合情侣散步、家庭游览或独自慢行。节假日与周末游客较多，热门路段和码头可能出现人流密集，建议早晨或傍晚避开高峰，并考虑租借自行车或选择较安静的支线路径。',
        '此线路以悠闲漫步为主，兼顾自然风光与宗教文化体验（如灵隐寺）。整体体验舒适，适合放慢节奏享受江南水乡的气息与当地茶文化。秋季天气温和，拍照与户外活动条件良好，是追求慢旅行与摄影的理想选择。',
        '推荐理由：西湖秋景具备高度的观赏与摄影价值，苏堤与三潭印月等经典景点合力展现江南美学；灵隐寺则为行程增添文化与心灵层面的体验。无论是短途周边游还是深度文化游，该路线都能提供平衡的视觉与体验收益。',
        4.5, 2);


