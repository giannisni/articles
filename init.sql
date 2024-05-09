-- Drop existing tables if they exist to allow reinitialization
DROP TABLE IF EXISTS public.comments;
DROP TABLE IF EXISTS public.articles;
DROP TABLE IF EXISTS public.app_users;

-- Create the 'app_users' table
CREATE TABLE public.app_users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL
);

-- Create the 'articles' table
CREATE TABLE public.articles (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    abstract_text TEXT,
    publication_date TIMESTAMP WITHOUT TIME ZONE,
    user_id INTEGER,
    CONSTRAINT fk_articles_users FOREIGN KEY (user_id)
        REFERENCES public.app_users (id) ON UPDATE NO ACTION ON DELETE SET NULL
);

-- Create the 'comments' table
CREATE TABLE public.comments (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    text VARCHAR(255) NOT NULL,
    article_id INTEGER,
    user_id INTEGER,
    CONSTRAINT fk_comments_articles FOREIGN KEY (article_id)
        REFERENCES public.articles (id) ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT fk_comments_users FOREIGN KEY (user_id)
        REFERENCES public.app_users (id) ON UPDATE NO ACTION ON DELETE SET NULL
);

-- Insert sample data into 'app_users'
INSERT INTO public.app_users (username) VALUES
('user1'),
('user2');

-- Insert sample data into 'articles'
INSERT INTO public.articles (title, abstract_text, publication_date, user_id) VALUES
('Exploring Spring Boot', 'A deep dive into the capabilities and features of Spring Boot for building modern microservices.', '2024-04-30 14:15:22.000+00:00', (SELECT id FROM public.app_users WHERE username = 'user1')),
('Introduction to Docker', 'Learn how to use Docker for deploying containerized applications.', '2024-05-01 09:00:00.000+00:00', (SELECT id FROM public.app_users WHERE username = 'user2'));

-- Insert sample data into 'comments'
INSERT INTO public.comments (created_at, text, article_id, user_id) VALUES
('2024-05-02 08:30:00.000+00:00', 'Great article on Spring Boot!', (SELECT id FROM public.articles WHERE title = 'Exploring Spring Boot'), (SELECT id FROM public.app_users WHERE username = 'user2')),
('2024-05-02 09:45:00.000+00:00', 'Very informative, thanks!', (SELECT id FROM public.articles WHERE title = 'Introduction to Docker'), (SELECT id FROM public.app_users WHERE username = 'user1'));
