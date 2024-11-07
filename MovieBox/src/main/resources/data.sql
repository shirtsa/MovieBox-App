INSERT INTO `roles`
VALUES (1, 'USER'), (2, 'ADMIN');

INSERT INTO `celebrities` (id, name, image_url, biography)
 VALUES
     (1, 'Johnny Depp', 'https://images.hellomagazine.com/horizon/landscape/eef37c2f3656-johnny-depp-facts-t.jpg', 'John Christopher "Johnny" Depp II was born on June 9, 1963 in Owensboro, Kentucky, to Betty Sue Palmer (née Wells), a waitress, and John Christopher Depp, a civil engineer. He was raised in Florida. He dropped out of school when he was 15, and fronted a series of music-garage bands, including one named The Kids.'),
     (2, 'Jim Carrey', 'https://faroutmagazine.co.uk/static/uploads/1/2023/03/Jim-Carrey-Lloyd-Christmas-Dumb-and-Dumber-1994-Far-Out-Magazine-F.jpg', 'Jim Carrey, Canadian-born and a U.S. citizen since 2004, is an actor and producer famous for his rubbery body movements and flexible facial expressions. The two-time Golden Globe-winner rose to fame as a cast member of the Fox sketch comedy In Living Color (1990) but leading roles in Ace Ventura: Pet Detective (1994), Dumb and Dumber (1994) and The Mask (1994) established him as a bankable comedy actor.'),
     (3, 'Leonardo DiCaprio', 'https://variety.com/wp-content/uploads/2023/05/GettyImages-1492284655.jpg?w=1024', 'Few actors in the world have had a career quite as diverse as Leonardo DiCaprio''s. DiCaprio has gone from relatively humble beginnings, as a supporting cast member of the sitcom Growing Pains (1985) and low budget horror movies, such as Critters 3 (1991), to a major teenage heartthrob in the 1990s, as the hunky lead actor in movies such as Romeo + Juliet (1996) and Titanic (1997), to then become a leading man in Hollywood blockbusters.'),
     (4, 'Angelina Jolie', 'https://assets.vogue.com/photos/61852e738e54a43d46410db8/4:3/w_2560%2Cc_limit/GettyImages-1349812316.jpg', 'Angelina Jolie is an Academy Award-winning actress who rose to fame after her role in Girl, Interrupted (1999), playing the title role in the "Lara Croft" blockbuster movies, as well as Mr. & Mrs. Smith (2005), Wanted (2008), Salt (2010) and Maleficent (2014). Off-screen, Jolie has become prominently involved in international charity projects, especially those involving refugees.'),
     (5, 'Keira Knightley', 'https://image.tmdb.org/t/p/w500/bRC1B2VwV0wK3ElciFAK6QZf2wD.jpg', 'Keira Christina Knightley was born March 26, 1985 in the South West Greater London suburb of Richmond. She is the daughter of actor Will Knightley and actress turned playwright Sharman Macdonald. An older brother, Caleb Knightley, was born in 1979. Her father is English, while her Scottish-born mother is of Scottish and Welsh origin. Brought up immersed in the acting profession from both sides.'),
     (6, 'Keanu Reeves', 'https://media.newyorker.com/photos/5cf5728d47f7cc877a506d9b/master/pass/Fry-KeanuReeves-2.jpg', 'Keanu Charles Reeves, whose first name means "cool breeze over the mountains" in Hawaiian, was born September 2, 1964 in Beirut, Lebanon. He is the son of Patric Reeves, a showgirl and costume designer, and Samuel Nowlin Reeves, a geologist. Keanu''s father was born in Hawaii, of Native Hawaiian and Chinese ancestry while Keanu''s mother is originally from Essex England.'),
     (7, 'Megan Fox', 'https://media-cldnry.s-nbcnews.com/image/upload/t_fit-560w,f_auto,q_auto:best/rockcms/2022-06/megan-fox-mc-220628-04-7349c3.jpg', 'Megan Denise Fox was born on May 16, 1986 in Oak Ridge, Tennessee and raised in Rockwood, Tennessee to Gloria Darlene Tonachio (née Cisson), a real estate manager and Franklin Thomas Fox, a parole officer. She began her drama and dance training at age 5 and at age 10, she moved to Port St. Lucie, Florida where she continued her training and finished school.'),
     (8, 'Sydney Sweeney', 'https://images.foxtv.com/static.fox29.com/www.fox29.com/content/uploads/2022/07/932/524/GettyImages-1401282832.jpg?ve=1&tl=1', 'Sydney Sweeney (born September 12, 1997) is an American actress best known for her roles as Haley Caren on In the Vault (2017) and Emaline Addario on the Netflix series Everything Sucks! (2018). Sweeney is set to star in recurring roles in the HBO miniseries Sharp Objects (2018) starring Amy Adams and the Hulu series The Handmaid''s Tale (2017) with Elisabeth Moss.');

INSERT INTO `news` (id, name, first_image_url, second_image_url, news_type, description, trailer_url, created)
VALUES
    (1, 'Deadpool & Wolverine', 'https://lumiere-a.akamaihd.net/v1/images/deadpool_wolverine_mobile_640x480_ad8020fd.png', 'https://m.media-amazon.com/images/I/81FQBhYvaxL.jpg', 'COMING_SOON', 'Deadpool & Wolverine is a 2024 American superhero film based on Marvel Comics featuring the characters Deadpool and Wolverine, produced by Marvel Studios, Maximum Effort, and 21 Laps Entertainment and distributed by Walt Disney Studios Motion Pictures.', '73_1biulkYk', CURRENT_TIMESTAMP),
    (2, 'Despicable Me 4', 'https://www.icetheaters.com/sites/default/files/2024-02/despicable%20me%204.jpg', 'https://movies.universalpictures.com/media/06-dm4-dm-mobile-banner-1080x745-now-rr-f01-062524-668591e542993-1.jpg', 'COMING_SOON', 'Despicable Me 4 is a 2024 American animated comedy film produced by Illumination and distributed by Universal Pictures. It is the sequel to Despicable Me 3 (2017), the fourth main installment, and the sixth overall installment in the Despicable Me franchise.', 'LtNYaH61dXY', CURRENT_TIMESTAMP),
    (3, 'It Ends With Us', 'https://m.media-amazon.com/images/M/MV5BYzM2NGMzNGQtZjNhMi00MTVkLTg2ZGQtN2M4OTllYzU1Y2Y0XkEyXkFqcGc@._V1_.jpg', 'https://deadline.com/wp-content/uploads/2023/01/Blake-Lively-and-Justin-Baldoni.jpg?w=681&h=383&crop=1', 'NEW_ARRIVAL', 't Ends with Us is an upcoming American romantic drama film directed by Justin Baldoni from a screenplay by Christy Hall, based on the 2016 novel of the same name by Colleen Hoover. The film stars Blake Lively, Baldoni, Brandon Sklenar, Jenny Slate, and Hasan Minhaj.', 'r-GQvSc5ZGw', CURRENT_TIMESTAMP),
    (4, 'Twisters', 'https://movies.universalpictures.com/media/twisters-poster-663b97a67029f-1.jpg', 'https://ntvb.tmsimg.com/assets/p26477529_v_h8_ac.jpg?w=1280&h=720', 'NEW_ARRIVAL', 'Twisters is a 2024 American disaster film directed by Lee Isaac Chung from a screenplay by Mark L. Smith, based on a story by Joseph Kosinski. Serving as a standalone sequel to Twister (1996), the film stars Daisy Edgar-Jones, Glen Powell, Anthony Ramos, Brandon Perea, Maura Tierney, and Sasha Lane.', 'wdok0rZdmx4', CURRENT_TIMESTAMP);

# INSERT INTO `productions` (id, name, description, genre, image_url, length, production_type, rating, rent_price, video_url, year)
# VALUES (1, 'Alien: Romulus', 'While scavenging the deep ends of a derelict space station, a group of young space colonizers come face to face with the most terrifying life form in the universe.', 'HORROR', 'https://wiziwiz.com/wp-content/uploads/2024/06/wiziwiz-alien-romulus-poster-.jpg', '132', 'MOVIE', '5', '10', 'x0XDEhP4MQs', '2024'),
#        (2, 'Stalker', 'Stalker (Russian: Сталкер, IPA: is a 1979 Soviet science fiction film directed by Andrei Tarkovsky with a screenplay written by Arkady and Boris Strugatsky, loosely based on their 1972 novel Roadside Picnic. The film tells the story of an expedition led by a figure known as the "Stalker" (Alexander Kaidanovsky), who guides his two clients—a melancholic writer (Anatoly Solonitsyn) seeking inspiration, and a professor (Nikolai Grinko) seeking scientific discovery—through a hazardous wasteland to a mysterious restricted site known simply as the "Zone", where there supposedly exists a room which grants a person''s innermost desires. The film combines elements of science fiction and fantasy with dramatic philosophical, and psychological themes.', 'ADVENTURE', 'https://www.sensesofcinema.com/wp-content/uploads/2014/02/stalker-640x400.jpg', '184', 'MOVIE', '5', '4', 'YuOnfQd-aTw', '1979'),
#        (3, 'Forrest Gump', 'The film follows the life of an Alabama man named Forrest Gump (Hanks) and his experiences in the 20th-century United States. Principal photography took place between August and December 1993, mainly in Georgia, North Carolina and South Carolina. Extensive visual effects were used to incorporate Hanks into archived footage and to develop other scenes. The soundtrack features songs reflecting the different periods seen in the film.', 'DRAMA', 'https://m.media-amazon.com/images/I/91++WV6FP4L._AC_UF894,1000_QL80_.jpg', '142', 'MOVIE', '5', '8', 'bLvqoHBptjg', '1994'),
#        (4, 'Fight Club', 'Fight Club is a 1999 American film directed by David Fincher, and starring Brad Pitt, Edward Norton and Helena Bonham Carter. It is based on the 1996 novel by Chuck Palahniuk. Norton plays the unnamed narrator, who is discontented with his white-collar job. He forms a "fight club" with soap salesman Tyler Durden (Pitt), and becomes embroiled in a relationship with an impoverished but beguilingly attractive woman, Marla Singer (Bonham Carter).', 'DRAMA', 'https://m.media-amazon.com/images/M/MV5BMjk3NTYyMzc4Nl5BMl5BanBnXkFtZTcwODU3ODMzMw@@._V1_.jpg', '139', 'MOVIE', '5', '6', 'qtRKdVHc-cE', '1999'),
#        (5, 'Gladiator', 'Crowe portrays the Roman general Maximus Decimus Meridius, who is betrayed when Commodus, the ambitious son of Emperor Marcus Aurelius, murders his father and seizes the throne. Reduced to slavery, Maximus becomes a gladiator and rises through the ranks of the arena, determined to avenge the murders of his family and the emperor.', 'ACTION', 'https://m.media-amazon.com/images/I/51GA6V6VE1L._AC_UF894,1000_QL80_.jpg', '153', 'MOVIE', '5', '4', 'P5ieIbInFpg', '2000'),
#        (6, 'Oppenheimer', 'Oppenheimer is a 2023 epic biographical thriller drama film[a] written, directed, and produced by Christopher Nolan.[8] It follows the life of J. Robert Oppenheimer, the American theoretical physicist who helped develop the first nuclear weapons during World War II. Based on the 2005 biography American Prometheus by Kai Bird and Martin J. Sherwin, the film chronicles Oppenheimer''s studies, his direction of the Los Alamos Laboratory and his 1954 security hearing. Cillian Murphy stars as Oppenheimer, alongside Robert Downey Jr. ', 'HISTORICAL', 'https://images.indianexpress.com/2023/05/nolan-movie.jpg', '180', 'MOVIE', '5', '9', 'bK6ldnjE3Y0', '2023'),
#        (7, 'Friends', 'Friends is an American television sitcom created by David Crane and Marta Kauffman, which aired on NBC from September 22, 1994, to May 6, 2004, lasting ten seasons.[1] With an ensemble cast starring Jennifer Aniston, Courteney Cox, Lisa Kudrow, Matt LeBlanc, Matthew Perry and David Schwimmer, the show revolves around six friends in their 20s and early 30s who live in Manhattan, New York City. The original executive producers were Kevin S. Bright, Kauffman, and Crane.', 'COMEDY', 'https://uniathenaprods3.uniathena.com/s3fs-public/insights-article/seriesreview-e2-80-9cfriends-e2-80-9d_0.jpg', '30', 'TV', '5', '10', 'IEEbUzffzrk&t=2s', '1994'),
#        (8, 'Breaking Bad', 'Breaking Bad is an American crime drama television series created and produced by Vince Gilligan for AMC. Set and filmed in Albuquerque, New Mexico, the series follows Walter White (Bryan Cranston), an underpaid, dispirited high-school chemistry teacher struggling with a recent diagnosis of stage-three lung cancer.', 'ADVENTURE', 'https://ntvb.tmsimg.com/assets/p8696131_b_h10_aa.jpg?w=960&h=540', '45', 'TV', '4', '8', 'HhesaQXLuRY', '2008'),
#        (9, 'Game of Thrones', 'Game of Thrones is an American fantasy drama television series created by David Benioff and D. B. Weiss for HBO. It is an adaptation of A Song of Ice and Fire, a series of fantasy novels by George R. R. Martin, the first of which is A Game of Thrones. The show premiered on HBO in the United States on April 17, 2011, and concluded on May 19, 2019, with 73 episodes broadcast over eight seasons.', 'FANTASY', 'https://assets-prd.ignimgs.com/2022/01/14/gameofthrones-allseasons-sq-1642120207458.jpg', '60', 'TV', '5', '7', 'bjqEWgDVPe0', '2011'),
#        (10, 'The Office', 'The Office is an American mockumentary sitcom television series based on the 2001–2003 BBC series of the same name created by (and starring) Ricky Gervais and Stephen Merchant. Adapted for NBC by Greg Daniels, a veteran writer for Saturday Night Live, King of the Hill, and The Simpsons.', 'COMEDY', 'https://ntvb.tmsimg.com/assets/p7893514_b_h8_ab.jpg?w=960&h=540', '22', 'TV', '3', '5', 'tNcDHWpselE', '2005'),
#        (11, 'Black Mirror', 'Black Mirror is a British anthology television series created by Charlie Brooker. The series explores various genres, with most episodes set in near-future dystopias containing sci-fi technology—a type of speculative fiction. The series is inspired by The Twilight Zone and uses the themes of technology and media to comment on contemporary social issues.', 'THRILLER', 'https://images.justwatch.com/poster/307079555/s332/season-6', '60', 'TV', '4', '9', 'V0XOApF5nLU', '2011'),
#        (12, 'House', 'House (also called House, M.D.) is an American medical drama television series that originally ran on the Fox network for eight seasons, from November 16, 2004, to May 21, 2012. Its main character, Dr. Gregory House (Hugh Laurie), is an unconventional, misanthropic, cynical medical genius who, despite his dependence on pain medication, leads a team of diagnosticians at the fictional Princeton–Plainsboro Teaching Hospital (PPTH) in New Jersey.', 'DRAMA', 'https://pics.filmaffinity.com/house_m_d-469252061-large.jpg', '45', 'TV', '5', '3', 'MczMB8nU1sY', '2004');
