package service;

import DAO.MovieDAOBusiness;
import entity.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MovieBusiness {
  private static MovieDAOBusiness movieDAOBusiness;

  public MovieBusiness() {
      movieDAOBusiness = new MovieDAOBusiness();
  }

    public static void addMovie(Scanner scanner) {
        System.out.print("Nhập tiêu đề phim: ");
        String title = scanner.nextLine();
        System.out.print("Nhập đạo diễn: ");
        String director = scanner.nextLine();
        System.out.print("Nhập thời lượng phim (phút): ");
        int duration = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhập ngày phát hành (yyyy-MM-dd): ");
        LocalDate releaseDate = LocalDate.parse(scanner.nextLine());

        Movie movie = new Movie(title, director, duration, releaseDate);
        boolean success = movieDAOBusiness.addMovie(movie);
        System.out.println(success ? "Thêm phim thành công!" : "Thêm phim thất bại!");
    }

    public static void updateMovie(Scanner scanner) {
        System.out.print("Nhập movie_id cần cập nhật: ");
        int movieId = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhập tiêu đề phim mới: ");
        String title = scanner.nextLine();
        System.out.print("Nhập đạo diễn mới: ");
        String director = scanner.nextLine();
        System.out.print("Nhập thời lượng phim mới (phút): ");
        int duration = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhập ngày phát hành mới (yyyy-MM-dd): ");
        LocalDate releaseDate = LocalDate.parse(scanner.nextLine());

        Movie movie = new Movie(title, director, duration, releaseDate);
        movie.setMovieId(movieId);
        boolean success = movieDAOBusiness.updateMovie(movie);
        System.out.println(success ? "Cập nhật thành công!" : "Cập nhật thất bại!");
    }

    public static void deleteMovie(Scanner scanner) {
        System.out.print("Nhập movie_id cần xóa: ");
        int movieId = Integer.parseInt(scanner.nextLine());
        boolean success = movieDAOBusiness.deleteMovie(movieId);
        System.out.println(success ? "Xóa thành công!" : "Xóa thất bại!");
    }

    public static void displayMovies() {
        List<Movie> movies = movieDAOBusiness.getAllActiveMovies();
        if (movies.isEmpty()) {
            System.out.println("Không có phim nào!");
        } else {
            movies.forEach(System.out::println);
        }
    }
}
