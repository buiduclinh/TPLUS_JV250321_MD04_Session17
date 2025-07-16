package DAO;

import entity.Movie;
import util.DBHelper;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAOBusiness implements MovieDAO {

    @Override
    public boolean addMovie(Movie movie) {
        String sql = "{CALL add_movie(?,?,?,?)}";
        try (Connection conn = JDBCUtil.getInstance().getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, movie.getTitle());
            cs.setString(2, movie.getDirector());
            cs.setInt(3, movie.getDuration());
            cs.setDate(4, Date.valueOf(movie.getReleaseDate()));

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMovie(Movie movie) {
        String sql = "{CALL update_movie(?,?,?,?,?)}";
        try (Connection conn = JDBCUtil.getInstance().getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, movie.getMovieId());
            cs.setString(2, movie.getTitle());
            cs.setString(3, movie.getDirector());
            cs.setInt(4, movie.getDuration());
            cs.setDate(5, Date.valueOf(movie.getReleaseDate()));

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMovie(int movieId) {
        String sql = "{CALL delete_movie(?)}";
        try (Connection conn = JDBCUtil.getInstance().getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, movieId);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Movie> getAllActiveMovies() {
        String sql = "{CALL get_active_movies()}";
        return DBHelper.executeQuery(sql, rs -> {
            List<Movie> movies = new ArrayList<>();
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("title"));
                movie.setDirector(rs.getString("director"));
                movie.setDuration(rs.getInt("duration"));
                movie.setReleaseDate(rs.getDate("release_date").toLocalDate());
                movie.setStatus(rs.getBoolean("status"));
                movies.add(movie);
            }
            return movies;
        });
    }
}