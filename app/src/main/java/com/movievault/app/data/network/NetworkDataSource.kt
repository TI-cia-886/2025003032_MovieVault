package com.movievault.app.data.network

import com.movievault.app.data.network.dto.MovieDetailDto
import com.movievault.app.data.network.dto.MovieSearchDto
import com.movievault.app.data.network.dto.OmdbResponse
import kotlinx.coroutines.delay

class NetworkDataSource(private val apiService: ApiService) {

    // ===== Mock Data (used when API key is not configured) =====

    private val mockMovies = listOf(
        MovieSearchDto(
            title = "The Shawshank Redemption",
            year = "1994",
            imdbId = "tt0111161",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BMDAyY2FhYjctNDc5OS00MDNlLThiMGUtY2UxYWVkNGY2ZjljXkEyXkFqcGc@._V1_SX300.jpg"
        ),
        MovieSearchDto(
            title = "The Godfather",
            year = "1972",
            imdbId = "tt0068646",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BYTJkNGQyZDgtZDQ0NC00MDM0LWEzZWQtYzUzZDEwMDljZWNjXkEyXkFqcGc@._V1_SX300.jpg"
        ),
        MovieSearchDto(
            title = "The Dark Knight",
            year = "2008",
            imdbId = "tt0468569",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg"
        ),
        MovieSearchDto(
            title = "Pulp Fiction",
            year = "1994",
            imdbId = "tt0110912",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BYTViYTE3ZGQtNDBlMC00ZTAyLTkyODMtZGRiZDg0MjA2YThkXkEyXkFqcGc@._V1_SX300.jpg"
        ),
        MovieSearchDto(
            title = "Inception",
            year = "2010",
            imdbId = "tt1375666",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg"
        ),
        MovieSearchDto(
            title = "Fight Club",
            year = "1999",
            imdbId = "tt0137523",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BOTgyOGQ1NDItNGU3Ny00MjU3LTg2YWEtNmEyYjBiMjI1Y2M5XkEyXkFqcGc@._V1_SX300.jpg"
        ),
        MovieSearchDto(
            title = "Forrest Gump",
            year = "1994",
            imdbId = "tt0109830",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BNDYwNzVjMTYtZmU5YzQtYTg3OC00NDkzLWI0MmEtMzA3MDQzOWNiY2ZlXkEyXkFqcGc@._V1_SX300.jpg"
        ),
        MovieSearchDto(
            title = "The Matrix",
            year = "1999",
            imdbId = "tt0133093",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BN2NmN2VhMTQtMDNiOS00NDlhLTliMjgtODE2ZDYxZjlhZjhkXkEyXkFqcGc@._V1_SX300.jpg"
        ),
        MovieSearchDto(
            title = "Interstellar",
            year = "2014",
            imdbId = "tt0816692",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BYzdjMDAxZGItMjI2My00ODA1LTlkNzItOWFjMDU5ZDJlYWY3XkEyXkFqcGc@._V1_SX300.jpg"
        ),
        MovieSearchDto(
            title = "Spirited Away",
            year = "2001",
            imdbId = "tt0245429",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BMjlmZmI5MDctNDE2YS00YWE0LWE5ZWItZDBhYWQ0NTcxNWRhXkEyXkFqcGc@._V1_SX300.jpg"
        ),
        MovieSearchDto(
            title = "Parasite",
            year = "2019",
            imdbId = "tt6751668",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BYjk1Y2U4MjQtY2ZiNS00OWQyLWI3MmYtZWUwNmRjYWRiNWNhXkEyXkFqcGc@._V1_SX300.jpg"
        ),
        MovieSearchDto(
            title = "The Lord of the Rings",
            year = "2001",
            imdbId = "tt0120737",
            type = "movie",
            poster = "https://m.media-amazon.com/images/M/MV5BNzIxMDQ2YTctNDY4MC00ZTRhLTk4ODQtMTVlOWY4NGZmZGNkXkEyXkFqcGc@._V1_SX300.jpg"
        )
    )

    private val mockDetails = mapOf(
        "tt0111161" to MovieDetailDto(
            title = "The Shawshank Redemption", year = "1994", rated = "R",
            released = "14 Oct 1994", runtime = "142 min",
            genre = "Drama", director = "Frank Darabont",
            writer = "Stephen King, Frank Darabont",
            actors = "Tim Robbins, Morgan Freeman, Bob Gunton",
            plot = "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.",
            language = "English", country = "United States", awards = "Nominated for 7 Oscars",
            poster = "https://m.media-amazon.com/images/M/MV5BMDAyY2FhYjctNDc5OS00MDNlLThiMGUtY2UxYWVkNGY2ZjljXkEyXkFqcGc@._V1_SX300.jpg",
            imdbRating = "9.3", imdbVotes = "2,952,306", imdbId = "tt0111161",
            type = "movie", response = "True"
        ),
        "tt0068646" to MovieDetailDto(
            title = "The Godfather", year = "1972", rated = "R",
            released = "24 Mar 1972", runtime = "175 min",
            genre = "Crime, Drama", director = "Francis Ford Coppola",
            writer = "Mario Puzo, Francis Ford Coppola",
            actors = "Marlon Brando, Al Pacino, James Caan",
            plot = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant youngest son.",
            language = "English, Italian, Latin", country = "United States", awards = "Won 3 Oscars",
            poster = "https://m.media-amazon.com/images/M/MV5BYTJkNGQyZDgtZDQ0NC00MDM0LWEzZWQtYzUzZDEwMDljZWNjXkEyXkFqcGc@._V1_SX300.jpg",
            imdbRating = "9.2", imdbVotes = "2,038,995", imdbId = "tt0068646",
            type = "movie", response = "True"
        ),
        "tt0468569" to MovieDetailDto(
            title = "The Dark Knight", year = "2008", rated = "PG-13",
            released = "18 Jul 2008", runtime = "152 min",
            genre = "Action, Crime, Drama", director = "Christopher Nolan",
            writer = "Jonathan Nolan, Christopher Nolan, David S. Goyer",
            actors = "Christian Bale, Heath Ledger, Aaron Eckhart",
            plot = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
            language = "English, Mandarin", country = "United States, United Kingdom", awards = "Won 2 Oscars",
            poster = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg",
            imdbRating = "9.0", imdbVotes = "2,914,136", imdbId = "tt0468569",
            type = "movie", response = "True"
        ),
        "tt0110912" to MovieDetailDto(
            title = "Pulp Fiction", year = "1994", rated = "R",
            released = "14 Oct 1994", runtime = "154 min",
            genre = "Crime, Drama", director = "Quentin Tarantino",
            writer = "Quentin Tarantino, Roger Avary",
            actors = "John Travolta, Uma Thurman, Samuel L. Jackson",
            plot = "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
            language = "English, Spanish, French", country = "United States", awards = "Won 1 Oscar",
            poster = "https://m.media-amazon.com/images/M/MV5BYTViYTE3ZGQtNDBlMC00ZTAyLTkyODMtZGRiZDg0MjA2YThkXkEyXkFqcGc@._V1_SX300.jpg",
            imdbRating = "8.9", imdbVotes = "2,261,912", imdbId = "tt0110912",
            type = "movie", response = "True"
        ),
        "tt1375666" to MovieDetailDto(
            title = "Inception", year = "2010", rated = "PG-13",
            released = "16 Jul 2010", runtime = "148 min",
            genre = "Action, Adventure, Sci-Fi", director = "Christopher Nolan",
            writer = "Christopher Nolan",
            actors = "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page",
            plot = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
            language = "English, Japanese, French", country = "United States, United Kingdom", awards = "Won 4 Oscars",
            poster = "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg",
            imdbRating = "8.8", imdbVotes = "2,582,315", imdbId = "tt1375666",
            type = "movie", response = "True"
        ),
        "tt0137523" to MovieDetailDto(
            title = "Fight Club", year = "1999", rated = "R",
            released = "15 Oct 1999", runtime = "139 min",
            genre = "Drama", director = "David Fincher",
            writer = "Chuck Palahniuk, Jim Uhls",
            actors = "Brad Pitt, Edward Norton, Meat Loaf",
            plot = "An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.",
            language = "English", country = "United States, Germany", awards = "Nominated for 1 Oscar",
            poster = "https://m.media-amazon.com/images/M/MV5BOTgyOGQ1NDItNGU3Ny00MjU3LTg2YWEtNmEyYjBiMjI1Y2M5XkEyXkFqcGc@._V1_SX300.jpg",
            imdbRating = "8.8", imdbVotes = "2,363,072", imdbId = "tt0137523",
            type = "movie", response = "True"
        ),
        "tt0109830" to MovieDetailDto(
            title = "Forrest Gump", year = "1994", rated = "PG-13",
            released = "06 Jul 1994", runtime = "142 min",
            genre = "Drama, Romance", director = "Robert Zemeckis",
            writer = "Winston Groom, Eric Roth",
            actors = "Tom Hanks, Robin Wright, Gary Sinise",
            plot = "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.",
            language = "English", country = "United States", awards = "Won 6 Oscars",
            poster = "https://m.media-amazon.com/images/M/MV5BNDYwNzVjMTYtZmU5YzQtYTg3OC00NDkzLWI0MmEtMzA3MDQzOWNiY2ZlXkEyXkFqcGc@._V1_SX300.jpg",
            imdbRating = "8.8", imdbVotes = "2,289,134", imdbId = "tt0109830",
            type = "movie", response = "True"
        ),
        "tt0133093" to MovieDetailDto(
            title = "The Matrix", year = "1999", rated = "R",
            released = "31 Mar 1999", runtime = "136 min",
            genre = "Action, Sci-Fi", director = "Lana Wachowski, Lilly Wachowski",
            writer = "Lilly Wachowski, Lana Wachowski",
            actors = "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
            plot = "When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth - the life he knows is the elaborate deception of an evil cyber-intelligence.",
            language = "English", country = "United States, Australia", awards = "Won 4 Oscars",
            poster = "https://m.media-amazon.com/images/M/MV5BN2NmN2VhMTQtMDNiOS00NDlhLTliMjgtODE2ZDYxZjlhZjhkXkEyXkFqcGc@._V1_SX300.jpg",
            imdbRating = "8.7", imdbVotes = "2,086,311", imdbId = "tt0133093",
            type = "movie", response = "True"
        ),
        "tt0816692" to MovieDetailDto(
            title = "Interstellar", year = "2014", rated = "PG-13",
            released = "07 Nov 2014", runtime = "169 min",
            genre = "Adventure, Drama, Sci-Fi", director = "Christopher Nolan",
            writer = "Jonathan Nolan, Christopher Nolan",
            actors = "Matthew McConaughey, Anne Hathaway, Jessica Chastain",
            plot = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
            language = "English", country = "United States, United Kingdom, Canada", awards = "Won 1 Oscar",
            poster = "https://m.media-amazon.com/images/M/MV5BYzdjMDAxZGItMjI2My00ODA1LTlkNzItOWFjMDU5ZDJlYWY3XkEyXkFqcGc@._V1_SX300.jpg",
            imdbRating = "8.7", imdbVotes = "2,173,775", imdbId = "tt0816692",
            type = "movie", response = "True"
        ),
        "tt0245429" to MovieDetailDto(
            title = "Spirited Away", year = "2001", rated = "PG",
            released = "28 Mar 2003", runtime = "125 min",
            genre = "Animation, Adventure, Family", director = "Hayao Miyazaki",
            writer = "Hayao Miyazaki",
            actors = "Rumi Hiiragi, Miyu Irino, Mari Natsuki",
            plot = "During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.",
            language = "Japanese", country = "Japan", awards = "Won 1 Oscar",
            poster = "https://m.media-amazon.com/images/M/MV5BMjlmZmI5MDctNDE2YS00YWE0LWE5ZWItZDBhYWQ0NTcxNWRhXkEyXkFqcGc@._V1_SX300.jpg",
            imdbRating = "8.6", imdbVotes = "864,172", imdbId = "tt0245429",
            type = "movie", response = "True"
        ),
        "tt6751668" to MovieDetailDto(
            title = "Parasite", year = "2019", rated = "R",
            released = "08 Nov 2019", runtime = "132 min",
            genre = "Comedy, Drama, Thriller", director = "Bong Joon Ho",
            writer = "Bong Joon Ho, Han Jin-won",
            actors = "Kang-ho Song, Sun-kyun Lee, Yeo-jeong Jo",
            plot = "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.",
            language = "Korean, English", country = "South Korea", awards = "Won 4 Oscars",
            poster = "https://m.media-amazon.com/images/M/MV5BYjk1Y2U4MjQtY2ZiNS00OWQyLWI3MmYtZWUwNmRjYWRiNWNhXkEyXkFqcGc@._V1_SX300.jpg",
            imdbRating = "8.5", imdbVotes = "973,872", imdbId = "tt6751668",
            type = "movie", response = "True"
        ),
        "tt0120737" to MovieDetailDto(
            title = "The Lord of the Rings: The Fellowship of the Ring", year = "2001", rated = "PG-13",
            released = "19 Dec 2001", runtime = "178 min",
            genre = "Action, Adventure, Drama", director = "Peter Jackson",
            writer = "J.R.R. Tolkien, Fran Walsh, Philippa Boyens",
            actors = "Elijah Wood, Ian McKellen, Orlando Bloom",
            plot = "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.",
            language = "English, Sindarin", country = "New Zealand, United States", awards = "Won 4 Oscars",
            poster = "https://m.media-amazon.com/images/M/MV5BNzIxMDQ2YTctNDY4MC00ZTRhLTk4ODQtMTVlOWY4NGZmZGNkXkEyXkFqcGc@._V1_SX300.jpg",
            imdbRating = "8.8", imdbVotes = "2,033,983", imdbId = "tt0120737",
            type = "movie", response = "True"
        )
    )

    private val useMockData = false // Set to false when you have a valid API key

    // ===== Public API =====

    suspend fun searchMovies(query: String, page: Int = 1): Result<List<MovieSearchDto>> {
        if (useMockData) {
            return searchMock(query)
        }
        return try {
            val response: OmdbResponse = apiService.searchMovies(query = query, page = page)
            if (response.response == "True") {
                Result.success(response.search ?: emptyList())
            } else {
                Result.failure(Exception(response.error ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMovieDetail(imdbId: String): Result<MovieDetailDto> {
        if (useMockData) {
            return getMockDetail(imdbId)
        }
        return try {
            val response = apiService.getMovieDetail(imdbId = imdbId)
            if (response.response == "True") {
                Result.success(response)
            } else {
                Result.failure(Exception(response.error ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ===== Mock implementation =====

    private suspend fun searchMock(query: String): Result<List<MovieSearchDto>> {
        // Simulate network delay
        delay(800)
        val results = mockMovies.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.year.contains(query)
        }
        return Result.success(results)
    }

    private suspend fun getMockDetail(imdbId: String): Result<MovieDetailDto> {
        delay(500)
        val detail = mockDetails[imdbId]
        return if (detail != null) {
            Result.success(detail)
        } else {
            Result.failure(Exception("Movie not found in mock data"))
        }
    }
}
