

--================================================================================================================================

                        KeyHolder - возврат Id, сгенерированного БД при создании записи

@Override
    public Film addFilm(Film film) {
        String sql = " INSERT INTO films (name, description, releaseDate, duration, MPA) " +
                "VALUES(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        if (film.getGenres() != null) {
            updateFilmGenres(film.getId(), film.getGenres());
        }
        -----------------------------------------------------------------------------------------------------------------------
        if (film.getGenres().size() != 0) {
            film.getGenres().forEach(genre -> jdbcTemplate.update("INSERT INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES (?,?)"
            , film.getId(), genre.getId()));
        }
        if (film.getDirectors().size() != 0) {
            film.getDirectors().forEach(director -> jdbcTemplate.update("INSERT INTO FILM_DIRECT (FILM_ID, DIRECT_ID) VALUES (?,?)"
            , film.getId(), director.getId()));
        }
        ------------------------------------------------------------------------------------------------------------------------
        return film;
    }

    private void updateFilmGenres(Integer filmId, TreeSet<Genre> genreTreeSet) {
        final List<Genre> genres = new ArrayList<>(genreTreeSet);
        jdbcTemplate.batchUpdate("MERGE INTO FILM_GENRE KEY (FILM_ID, GENRE_ID) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, filmId);
                        ps.setInt(2, genres.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return genres.size();
                    }
                });
    }

--===================================================================================================================================

                                      Получение из БД

public Film getFilm(Integer id) {
  String sql = "select " +
                "f.FILM_ID, FILM_NAME, FILM_DESCRIPTION, FILM_RELEASE_DATE, FILM_DURATION, m.RATING_ID, RATING_NAME " +
                "from FILM f, RATING m where FILM_ID = ? ";
        List<Film> list = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), id);
        Film film = null;
        if (list.size() == 1) {
            film = list.stream().findFirst().get();
        }

static Film makeFilm(ResultSet rs) throws SQLException {

        Film film = Film.builder()
                .id(rs.getInt("FILM_ID"))
                .name(Objects.requireNonNull(rs.getString("FILM_NAME")))
                .duration(rs.getInt("FILM_DURATION"))
                .description(Objects.requireNonNull(rs.getString("FILM_DESCRIPTION")))
                .releaseDate(Objects.requireNonNull(rs.getDate("FILM_RELEASE_DATE")).toLocalDate())
                .build();
        return film;
    }

-----------------------------------------------------------------------------------------------------------------------------------

    Map<Integer, List<Genre>> map = new HashMap<>();

            jdbcTemplate.query(sqlQuery, rs -> {
                Integer fId = rs.getInt("FILM_ID");

                List<Genre> list = map.computeIfAbsent(fId, k -> new ArrayList<>());
                list.add(Genre.builder()
                        .id(rs.getInt("GENRE_ID"))
                        .name(rs.getString("GENRE_NAME"))
                        .build());
            });
-----------------------------------------------------------------------------------------------------------------------------------
String sql = "some_request";
SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);

ArrayList<Genre> genres = new ArrayList<>();
while/if (rowSet.next()) {
                Genre genre = new Genre();
                genre.setId(filmGenreRow.getInt("genre_id"));
                genre.setName(genreDbStorage.getGenre(genre.getId()).getName());
                genres.add(genre);
            }
--===================================================================================================================================

                        Подсчёт суммы и возвращение значения Integer

private int makeUseful(Integer reviewId) {
        String sql = "SELECT SUM(USEFUL_VALUE) AS USEFUL_VALUE FROM REVIEWS_LIKES GROUP BY REVIEW_ID HAVING REVIEW_ID = ?";
        AtomicReference<Integer> result = new AtomicReference<>(0);
        jdbcTemplate.query(sql, rs -> {
            result.set(rs.getInt("USEFUL_VALUE"));
        }, reviewId);
        return result.get();
    }

--===================================================================================================================================


-- Если поле объекта - List.of {Genge: id, name} и List.of {Likes: id}

SELECT f.id,
       f.name,
       f.description,
       f.releasedate,
       f.duration,
       r.mpa_id AS rating_id,
       r.name AS rating_name,
       array_agg(DISTINCT f_g.genre_id || ' ' || g.name
                 ORDER BY f_g.genre_id) AS genres_data,
       array_agg(DISTINCT l.user_id
                 ORDER BY l.user_id) AS likes_data  --,
--       array_agg(DISTINCT f_d.director_id || ',' || d.director_name
--                 ORDER BY f_d.director_id) AS directors_data
FROM films AS f
LEFT JOIN mpa AS r ON r.mpa_id = f.mpa
LEFT JOIN film_genres AS f_g ON f_g.film_id = f.id
LEFT JOIN genres AS g ON g.genre_id = f_g.genre_id
LEFT JOIN likes AS l ON f.id = l.film_id
--LEFT JOIN film_director AS f_d ON f_d.film_id = f.id
--LEFT JOIN director AS d ON d.director_id = f_d.director_id
WHERE f.id = 4
GROUP BY f.id;



ResultSet genresDataResultSet = rs.getArray("genres_data").getResultSet();
        while (genresDataResultSet.next()) {
            String genreData = genresDataResultSet.getString(DATA_COLUMN);

            if (genreData == null) {
                break;
            }

            String[] data = genreData.split(" ");
            int genreId = Integer.parseInt(data[0]);
            String genreName = data[1];

            film.addGenre(new Genre(genreId, genreName));
        }

        genresDataResultSet.close();


--===================================================================================================================================

                      Рекомендации для пользователя по схожести с лайками фильмов другого пользователя

        @Override
            public List<Film> getRecommendations(int userId) {
                List<Integer> similarUserIds = findSimilarUsers(userId);
                System.out.println(similarUserIds);
                if (similarUserIds.isEmpty() || similarUserIds == null) {
                    List<Film> nullResult = new ArrayList<>();
                    return nullResult;
                }
                List<Film> recommendedFilms = findRecommendedFilms(userId, similarUserIds);
                if (recommendedFilms == null) {
                    recommendedFilms = new ArrayList<>();
                    return  recommendedFilms;
                }
                return recommendedFilms;
            }

            private List<Integer> findSimilarUsers(int userId) {
                String sql = "SELECT user2.user_id " +
                        "FROM likes AS user1 " +
                        "JOIN likes AS user2 ON user1.film_id = user2.film_id AND user1.user_id != user2.user_id " +
                        "WHERE user1.user_id = ? " +
                        "GROUP BY user2.user_id " +
                        "ORDER BY COUNT(*) DESC " +
                        "LIMIT 10";

                System.out.println(jdbcTemplate.queryForList(sql, Object.class, userId).stream()
                        .map(obj -> obj == null ? null : Integer.valueOf(obj.toString()))
                        .collect(Collectors.toList()));
                return jdbcTemplate.queryForList(sql, Object.class, userId).stream()
                        .map(obj -> obj == null ? null : Integer.valueOf(obj.toString()))
                        .collect(Collectors.toList());
            }

            private List<Film> findRecommendedFilms(int userId, List<Integer> similarUserIds) {
                String sql = "SELECT f.id " +
                        "FROM films AS f " +
                        "JOIN likes AS user1 ON user1.film_id = f.id AND user1.user_id = ? " +
                        "LEFT JOIN likes AS user2 ON user2.film_id = f.id AND user2.user_id = ANY(?) " +
                        "WHERE user2.user_id IS NULL " +
                        "GROUP BY f.id " +
                        "HAVING COUNT(*) >= 1 " +
                        "ORDER BY COUNT(*) DESC " +
                        "LIMIT 10";

                String sql2 = "SELECT l1.film_id\n" +
                        "FROM likes l1\n" +
                        "LEFT JOIN likes l2 ON l1.film_id = l2.film_id AND l2.user_id = ?\n" +
                        "WHERE l1.user_id = ? AND l2.user_id IS NULL;";

                Integer[] recommendedFilmsArray = similarUserIds.toArray(new Integer[0]);
                Set<Integer> result = new HashSet<>();
                for (int id : similarUserIds) {
                    List<Integer> recommendedFilms = jdbcTemplate.queryForList(sql2, Object.class, userId, recommendedFilmsArray).stream()
                            .map(obj -> obj == null ? null : Integer.valueOf(obj.toString()))
                            .collect(Collectors.toList());
                    result.addAll(recommendedFilms);
                }

                FilmDbStorage filmDbStorage = new FilmDbStorage(jdbcTemplate, new GenreDbStorage(jdbcTemplate), new MpaDbStorage(jdbcTemplate), new DirectorDbStorage(jdbcTemplate));
                List<Film> resultFilms = new ArrayList<>();
                for (Integer filmId : result) {
                    resultFilms.add(filmDbStorage.getFilmById(filmId));
                }
                return resultFilms;
            }
