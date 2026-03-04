--
-- PostgreSQL database dump
--

\restrict 93urzGAQw8n46agbVY0ndE0lKnlYZdzQV0fTDCRMMjL2qwflTcmrCLpzdKHfZsi

-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

-- Started on 2026-03-04 07:55:29

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE cargo_flow;
--
-- TOC entry 5351 (class 1262 OID 25124)
-- Name: cargo_flow; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE cargo_flow WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Italian_Italy.1252';


ALTER DATABASE cargo_flow OWNER TO postgres;

\unrestrict 93urzGAQw8n46agbVY0ndE0lKnlYZdzQV0fTDCRMMjL2qwflTcmrCLpzdKHfZsi
\connect cargo_flow
\restrict 93urzGAQw8n46agbVY0ndE0lKnlYZdzQV0fTDCRMMjL2qwflTcmrCLpzdKHfZsi

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 50620)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 5352 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS '';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 50621)
-- Name: adr_collo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.adr_collo (
    segnacollo text NOT NULL,
    id_adr_elemento integer NOT NULL,
    codice_imballaggio character varying(5) NOT NULL,
    qta double precision NOT NULL,
    um character varying(5) NOT NULL
);


ALTER TABLE public.adr_collo OWNER TO postgres;

--
-- TOC entry 5354 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE adr_collo; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.adr_collo IS 'nel sw considerare "etichettato" e "marcatura un"';


--
-- TOC entry 220 (class 1259 OID 50631)
-- Name: adr_elemento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.adr_elemento (
    numero_un integer NOT NULL,
    denominazione_trasporto character varying(255) NOT NULL,
    classe_adr character varying(5) NOT NULL,
    gruppo_imballaggio character varying(2) NOT NULL,
    codice_galleria character varying(5),
    qta_totale double precision NOT NULL,
    unita_misura character varying(5) NOT NULL,
    esenzione_lq boolean NOT NULL,
    esenzione_eq boolean NOT NULL
);


ALTER TABLE public.adr_elemento OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 50642)
-- Name: assicurazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.assicurazione (
    id_assicurazione uuid NOT NULL,
    descrizione character varying(25) NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.assicurazione OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 50650)
-- Name: autista; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.autista (
    cod_fisc character varying(20) NOT NULL,
    nome character varying(20) NOT NULL,
    cognome character varying(30) NOT NULL,
    sede uuid NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.autista OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 50660)
-- Name: autorizzazione_integrazione_indirizzo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.autorizzazione_integrazione_indirizzo (
    id uuid CONSTRAINT autorizzazione_indirizzo_id_not_null NOT NULL,
    id_movimento uuid CONSTRAINT autorizzazione_indirizzo_id_movimento_not_null NOT NULL,
    autorizzazione_path character varying(512) CONSTRAINT autorizzazione_indirizzo_autorizzazione_not_null NOT NULL,
    id_azienda uuid CONSTRAINT autorizzazione_indirizzo_id_azienda_not_null NOT NULL
);


ALTER TABLE public.autorizzazione_integrazione_indirizzo OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 50669)
-- Name: azienda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.azienda (
    id uuid NOT NULL,
    denominazione character varying(25) NOT NULL
);


ALTER TABLE public.azienda OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 50676)
-- Name: bordero; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bordero (
    id_bordero uuid NOT NULL,
    id_autista character varying(20) NOT NULL,
    id_macchina character varying(15) NOT NULL,
    data date NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.bordero OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 50686)
-- Name: bordero_merce; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bordero_merce (
    id_bordero uuid NOT NULL,
    segnacollo text NOT NULL,
    tassativo boolean DEFAULT false CONSTRAINT bordero_merce_tasativo_not_null NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.bordero_merce OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 50695)
-- Name: citta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.citta (
    cap character varying(10) NOT NULL,
    stato character varying(20) NOT NULL,
    citta character varying(25) NOT NULL
);


ALTER TABLE public.citta OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 50703)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    id uuid NOT NULL,
    ragione_sociale character varying(25) NOT NULL,
    indirizzo character varying(60) NOT NULL,
    cap character varying(10) NOT NULL,
    tel character varying(20),
    email character varying(20),
    id_azienda uuid NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 50712)
-- Name: consegna_avvenuta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.consegna_avvenuta (
    id_movimento uuid NOT NULL,
    firma_path character varying(255),
    id_azienda uuid NOT NULL
);


ALTER TABLE public.consegna_avvenuta OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 50718)
-- Name: consegna_fallita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.consegna_fallita (
    id bigint NOT NULL,
    id_movimento uuid NOT NULL,
    id_motivo bigint NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.consegna_fallita OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 50724)
-- Name: contrassegno_tipo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contrassegno_tipo (
    id bigint CONSTRAINT tipo_contrassegno_id_not_null NOT NULL,
    descrizione character varying(25) CONSTRAINT tipo_contrassegno_descrizione_not_null NOT NULL
);


ALTER TABLE public.contrassegno_tipo OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 50731)
-- Name: epal_cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.epal_cliente (
    id_cliente uuid NOT NULL,
    id uuid NOT NULL,
    addebito_epal integer NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.epal_cliente OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 50737)
-- Name: fasce_orarie; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fasce_orarie (
    id uuid CONSTRAINT fasce_orarie_id_not_null1 NOT NULL,
    dalle timestamp without time zone,
    alle timestamp without time zone
);


ALTER TABLE public.fasce_orarie OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 50741)
-- Name: fasce_orarie_incarico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fasce_orarie_incarico (
    id uuid CONSTRAINT fasce_orarie_id_not_null NOT NULL,
    incarico text CONSTRAINT fasce_orarie_incarico_not_null NOT NULL,
    id_azienda uuid NOT NULL,
    fascia_oraria uuid NOT NULL
);


ALTER TABLE public.fasce_orarie_incarico OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 50750)
-- Name: giorno_fasce_orarie; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.giorno_fasce_orarie (
    id_giorno uuid NOT NULL,
    id_fascia_oraria uuid NOT NULL
);


ALTER TABLE public.giorno_fasce_orarie OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 50755)
-- Name: giorno_preferito; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.giorno_preferito (
    id uuid NOT NULL,
    giorno integer NOT NULL
);


ALTER TABLE public.giorno_preferito OWNER TO postgres;

--
-- TOC entry 5355 (class 0 OID 0)
-- Dependencies: 236
-- Name: COLUMN giorno_preferito.giorno; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.giorno_preferito.giorno IS 'i giorni vanno da 1 a 7';


--
-- TOC entry 237 (class 1259 OID 50760)
-- Name: incarico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.incarico (
    ldv text NOT NULL,
    ddt text,
    data_consegna_prevista date NOT NULL,
    valore_doganale double precision,
    ritiro boolean DEFAULT false NOT NULL,
    valore_assicurazione double precision,
    mitt uuid NOT NULL,
    dest uuid NOT NULL,
    child text,
    vett_mitt uuid,
    id_azienda uuid NOT NULL,
    vett_dest uuid
);


ALTER TABLE public.incarico OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 50772)
-- Name: incarico_contrassegno; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.incarico_contrassegno (
    incarico text NOT NULL,
    contrassegno_tipo bigint NOT NULL,
    contrassegno_valore double precision NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.incarico_contrassegno OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 50780)
-- Name: incarico_sede_mitt_dest; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.incarico_sede_mitt_dest (
    incarico text CONSTRAINT incarico_sede_incarico_not_null NOT NULL,
    sede_mitt uuid CONSTRAINT incarico_sede_sede_mitt_not_null NOT NULL,
    sede_dest uuid CONSTRAINT incarico_sede_sede_dest_not_null NOT NULL,
    dal date CONSTRAINT incarico_sede_dal_not_null NOT NULL,
    fino_a date,
    id uuid NOT NULL,
    autorizzazione bigint,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.incarico_sede_mitt_dest OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 50790)
-- Name: manutenzione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.manutenzione (
    id_manutenzione uuid NOT NULL,
    targa_veicolo text NOT NULL,
    id_officina uuid,
    km integer NOT NULL,
    descrizione text NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.manutenzione OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 50799)
-- Name: merce; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.merce (
    segnacollo text NOT NULL,
    incarico text NOT NULL,
    merce_tipo bigint NOT NULL,
    peso_kg double precision,
    volume_m3 double precision,
    fragile boolean DEFAULT false,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.merce OWNER TO postgres;

--
-- TOC entry 5356 (class 0 OID 0)
-- Dependencies: 241
-- Name: COLUMN merce.fragile; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.merce.fragile IS 'implica impilabilità';


--
-- TOC entry 242 (class 1259 OID 50808)
-- Name: merce_epal; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.merce_epal (
    segnacollo text NOT NULL,
    id_epal text,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.merce_epal OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 50814)
-- Name: merce_tipo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.merce_tipo (
    id bigint NOT NULL,
    descrizione character varying(25) NOT NULL
);


ALTER TABLE public.merce_tipo OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 50821)
-- Name: merce_tipo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.merce_tipo ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.merce_tipo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 245 (class 1259 OID 50822)
-- Name: officina; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.officina (
    id uuid NOT NULL,
    ragione_sociale character varying(25) NOT NULL,
    indirizzo character varying(30) NOT NULL,
    cap character varying(10) NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.officina OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 50831)
-- Name: rinnovo_assicurazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rinnovo_assicurazione (
    id uuid NOT NULL,
    targa_veicolo character varying(10) NOT NULL,
    id_assicurazione uuid NOT NULL,
    dal date NOT NULL,
    fino_al date NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.rinnovo_assicurazione OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 50841)
-- Name: rinnovo_bollo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rinnovo_bollo (
    id uuid NOT NULL,
    targa_veicolo character varying(10) NOT NULL,
    dal date NOT NULL,
    fino_al date NOT NULL,
    importo double precision,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.rinnovo_bollo OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 50850)
-- Name: rinnovo_patente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rinnovo_patente (
    id uuid NOT NULL,
    id_autista character varying(20) NOT NULL,
    patente_tipo character varying(20) NOT NULL,
    dal date NOT NULL,
    al date NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.rinnovo_patente OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 50860)
-- Name: riserva; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.riserva (
    segnacollo text NOT NULL,
    descrizione character varying(1024) NOT NULL,
    firma character varying(512),
    id_azienda uuid NOT NULL
);


ALTER TABLE public.riserva OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 50867)
-- Name: sede; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sede (
    id uuid NOT NULL,
    sede character varying(50) NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.sede OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 50874)
-- Name: segnacollo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.segnacollo (
    segnacollo text NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.segnacollo OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 50880)
-- Name: sessione_carico_scarico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sessione_carico_scarico (
    id uuid CONSTRAINT sessione_carico_id_not_null NOT NULL,
    id_bordero uuid,
    vettore uuid,
    storico uuid CONSTRAINT sessione_carico_storico_not_null NOT NULL,
    note text,
    carico boolean NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.sessione_carico_scarico OWNER TO postgres;

--
-- TOC entry 5357 (class 0 OID 0)
-- Dependencies: 252
-- Name: TABLE sessione_carico_scarico; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.sessione_carico_scarico IS 'le informazioni di questa tabella servono per aggiornare lo storico';


--
-- TOC entry 253 (class 1259 OID 50888)
-- Name: stato; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stato (
    id bigint NOT NULL,
    descrizione character varying(25) NOT NULL,
    parent bigint
);


ALTER TABLE public.stato OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 50895)
-- Name: stato_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.stato ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.stato_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 255 (class 1259 OID 50896)
-- Name: storico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.storico (
    id_movimento uuid NOT NULL,
    segnacollo text CONSTRAINT storico_incarico_not_null NOT NULL,
    id_stato bigint NOT NULL,
    data_ora timestamp without time zone NOT NULL,
    sede uuid NOT NULL,
    id_azienda uuid NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.storico OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 50909)
-- Name: tipo_contrassegno_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.contrassegno_tipo ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tipo_contrassegno_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 257 (class 1259 OID 50910)
-- Name: veicolo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.veicolo (
    targa character varying(10) NOT NULL,
    telaio character varying(100),
    sede uuid NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.veicolo OWNER TO postgres;

--
-- TOC entry 5358 (class 0 OID 0)
-- Dependencies: 257
-- Name: TABLE veicolo; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.veicolo IS 'gli oggetti che si trovano solo in quest tabella e non anche in veicolo_lavoro sono mezzi personali';


--
-- TOC entry 258 (class 1259 OID 50917)
-- Name: veicolo_lavoro; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.veicolo_lavoro (
    targa character varying(10) NOT NULL,
    lato_corto real NOT NULL,
    lato_lungo real NOT NULL,
    altezza real NOT NULL,
    quintali_max real NOT NULL,
    id_azienda uuid NOT NULL
);


ALTER TABLE public.veicolo_lavoro OWNER TO postgres;

--
-- TOC entry 5306 (class 0 OID 50621)
-- Dependencies: 219
-- Data for Name: adr_collo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5307 (class 0 OID 50631)
-- Dependencies: 220
-- Data for Name: adr_elemento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5308 (class 0 OID 50642)
-- Dependencies: 221
-- Data for Name: assicurazione; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5309 (class 0 OID 50650)
-- Dependencies: 222
-- Data for Name: autista; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5310 (class 0 OID 50660)
-- Dependencies: 223
-- Data for Name: autorizzazione_integrazione_indirizzo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5311 (class 0 OID 50669)
-- Dependencies: 224
-- Data for Name: azienda; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5312 (class 0 OID 50676)
-- Dependencies: 225
-- Data for Name: bordero; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5313 (class 0 OID 50686)
-- Dependencies: 226
-- Data for Name: bordero_merce; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5314 (class 0 OID 50695)
-- Dependencies: 227
-- Data for Name: citta; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5315 (class 0 OID 50703)
-- Dependencies: 228
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5316 (class 0 OID 50712)
-- Dependencies: 229
-- Data for Name: consegna_avvenuta; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5317 (class 0 OID 50718)
-- Dependencies: 230
-- Data for Name: consegna_fallita; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5318 (class 0 OID 50724)
-- Dependencies: 231
-- Data for Name: contrassegno_tipo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5319 (class 0 OID 50731)
-- Dependencies: 232
-- Data for Name: epal_cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5320 (class 0 OID 50737)
-- Dependencies: 233
-- Data for Name: fasce_orarie; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5321 (class 0 OID 50741)
-- Dependencies: 234
-- Data for Name: fasce_orarie_incarico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5322 (class 0 OID 50750)
-- Dependencies: 235
-- Data for Name: giorno_fasce_orarie; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5323 (class 0 OID 50755)
-- Dependencies: 236
-- Data for Name: giorno_preferito; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5324 (class 0 OID 50760)
-- Dependencies: 237
-- Data for Name: incarico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5325 (class 0 OID 50772)
-- Dependencies: 238
-- Data for Name: incarico_contrassegno; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5326 (class 0 OID 50780)
-- Dependencies: 239
-- Data for Name: incarico_sede_mitt_dest; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5327 (class 0 OID 50790)
-- Dependencies: 240
-- Data for Name: manutenzione; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5328 (class 0 OID 50799)
-- Dependencies: 241
-- Data for Name: merce; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5329 (class 0 OID 50808)
-- Dependencies: 242
-- Data for Name: merce_epal; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5330 (class 0 OID 50814)
-- Dependencies: 243
-- Data for Name: merce_tipo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5332 (class 0 OID 50822)
-- Dependencies: 245
-- Data for Name: officina; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5333 (class 0 OID 50831)
-- Dependencies: 246
-- Data for Name: rinnovo_assicurazione; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5334 (class 0 OID 50841)
-- Dependencies: 247
-- Data for Name: rinnovo_bollo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5335 (class 0 OID 50850)
-- Dependencies: 248
-- Data for Name: rinnovo_patente; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5336 (class 0 OID 50860)
-- Dependencies: 249
-- Data for Name: riserva; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5337 (class 0 OID 50867)
-- Dependencies: 250
-- Data for Name: sede; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5338 (class 0 OID 50874)
-- Dependencies: 251
-- Data for Name: segnacollo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5339 (class 0 OID 50880)
-- Dependencies: 252
-- Data for Name: sessione_carico_scarico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5340 (class 0 OID 50888)
-- Dependencies: 253
-- Data for Name: stato; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5342 (class 0 OID 50896)
-- Dependencies: 255
-- Data for Name: storico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5344 (class 0 OID 50910)
-- Dependencies: 257
-- Data for Name: veicolo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5345 (class 0 OID 50917)
-- Dependencies: 258
-- Data for Name: veicolo_lavoro; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5359 (class 0 OID 0)
-- Dependencies: 244
-- Name: merce_tipo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.merce_tipo_id_seq', 1, false);


--
-- TOC entry 5360 (class 0 OID 0)
-- Dependencies: 254
-- Name: stato_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.stato_id_seq', 1, false);


--
-- TOC entry 5361 (class 0 OID 0)
-- Dependencies: 256
-- Name: tipo_contrassegno_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_contrassegno_id_seq', 1, false);


--
-- TOC entry 5007 (class 2606 OID 50928)
-- Name: adr_collo adr_collo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adr_collo
    ADD CONSTRAINT adr_collo_pk PRIMARY KEY (segnacollo);


--
-- TOC entry 5009 (class 2606 OID 50930)
-- Name: adr_elemento adr_elemento_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adr_elemento
    ADD CONSTRAINT adr_elemento_pk PRIMARY KEY (numero_un);


--
-- TOC entry 5011 (class 2606 OID 50932)
-- Name: assicurazione assicurazione_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assicurazione
    ADD CONSTRAINT assicurazione_pk PRIMARY KEY (id_assicurazione);


--
-- TOC entry 5013 (class 2606 OID 51395)
-- Name: autista autista_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autista
    ADD CONSTRAINT autista_pk PRIMARY KEY (cod_fisc);


--
-- TOC entry 5015 (class 2606 OID 50936)
-- Name: autorizzazione_integrazione_indirizzo autorizzazione_indirizzo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autorizzazione_integrazione_indirizzo
    ADD CONSTRAINT autorizzazione_indirizzo_pk PRIMARY KEY (id);


--
-- TOC entry 5017 (class 2606 OID 50938)
-- Name: azienda azienda_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.azienda
    ADD CONSTRAINT azienda_pk PRIMARY KEY (id);


--
-- TOC entry 5021 (class 2606 OID 50940)
-- Name: bordero_merce bordero_merce_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero_merce
    ADD CONSTRAINT bordero_merce_pk PRIMARY KEY (id_bordero, segnacollo);


--
-- TOC entry 5019 (class 2606 OID 50942)
-- Name: bordero bordero_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero
    ADD CONSTRAINT bordero_pk PRIMARY KEY (id_bordero);


--
-- TOC entry 5023 (class 2606 OID 51460)
-- Name: citta citta_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citta
    ADD CONSTRAINT citta_pk PRIMARY KEY (cap);


--
-- TOC entry 5025 (class 2606 OID 50946)
-- Name: cliente cliente_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pk PRIMARY KEY (id);


--
-- TOC entry 5027 (class 2606 OID 50948)
-- Name: consegna_avvenuta consegna_avvenuta_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_avvenuta
    ADD CONSTRAINT consegna_avvenuta_pk PRIMARY KEY (id_movimento);


--
-- TOC entry 5029 (class 2606 OID 50950)
-- Name: consegna_fallita consegna_fallita_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_fallita
    ADD CONSTRAINT consegna_fallita_pk PRIMARY KEY (id);


--
-- TOC entry 5033 (class 2606 OID 50952)
-- Name: epal_cliente epal_cliente_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.epal_cliente
    ADD CONSTRAINT epal_cliente_pk PRIMARY KEY (id);


--
-- TOC entry 5037 (class 2606 OID 50954)
-- Name: fasce_orarie_incarico fasce_orarie_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fasce_orarie_incarico
    ADD CONSTRAINT fasce_orarie_pk PRIMARY KEY (id);


--
-- TOC entry 5035 (class 2606 OID 50956)
-- Name: fasce_orarie fascia_oraria_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fasce_orarie
    ADD CONSTRAINT fascia_oraria_pk PRIMARY KEY (id);


--
-- TOC entry 5039 (class 2606 OID 50958)
-- Name: giorno_fasce_orarie giorno_fasce_orarie_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.giorno_fasce_orarie
    ADD CONSTRAINT giorno_fasce_orarie_pk PRIMARY KEY (id_giorno, id_fascia_oraria);


--
-- TOC entry 5041 (class 2606 OID 50960)
-- Name: giorno_preferito giorno_preferito_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.giorno_preferito
    ADD CONSTRAINT giorno_preferito_pk PRIMARY KEY (id);


--
-- TOC entry 5045 (class 2606 OID 50962)
-- Name: incarico_contrassegno incarico_contrassegno_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_contrassegno
    ADD CONSTRAINT incarico_contrassegno_pk PRIMARY KEY (incarico);


--
-- TOC entry 5043 (class 2606 OID 50964)
-- Name: incarico incarico_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_pk PRIMARY KEY (ldv);


--
-- TOC entry 5047 (class 2606 OID 50966)
-- Name: incarico_sede_mitt_dest incarico_sede_mitt_dest_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT incarico_sede_mitt_dest_pk PRIMARY KEY (id);


--
-- TOC entry 5049 (class 2606 OID 50968)
-- Name: manutenzione manutenzione_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manutenzione
    ADD CONSTRAINT manutenzione_pk PRIMARY KEY (id_manutenzione);


--
-- TOC entry 5053 (class 2606 OID 50970)
-- Name: merce_epal merce_epal_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce_epal
    ADD CONSTRAINT merce_epal_pk PRIMARY KEY (segnacollo);


--
-- TOC entry 5051 (class 2606 OID 50972)
-- Name: merce merce_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce
    ADD CONSTRAINT merce_pk PRIMARY KEY (segnacollo);


--
-- TOC entry 5055 (class 2606 OID 50974)
-- Name: merce_tipo merce_tipo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce_tipo
    ADD CONSTRAINT merce_tipo_pk PRIMARY KEY (id);


--
-- TOC entry 5057 (class 2606 OID 50976)
-- Name: officina officina_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.officina
    ADD CONSTRAINT officina_pk PRIMARY KEY (id);


--
-- TOC entry 5061 (class 2606 OID 50978)
-- Name: rinnovo_bollo rinnovo_bollo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_bollo
    ADD CONSTRAINT rinnovo_bollo_pk PRIMARY KEY (id);


--
-- TOC entry 5063 (class 2606 OID 50980)
-- Name: rinnovo_patente rinnovo_patente_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_patente
    ADD CONSTRAINT rinnovo_patente_pk PRIMARY KEY (id);


--
-- TOC entry 5065 (class 2606 OID 50982)
-- Name: riserva riserva_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.riserva
    ADD CONSTRAINT riserva_pk PRIMARY KEY (segnacollo);


--
-- TOC entry 5067 (class 2606 OID 50984)
-- Name: sede sede_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sede
    ADD CONSTRAINT sede_pk PRIMARY KEY (id);


--
-- TOC entry 5069 (class 2606 OID 50986)
-- Name: segnacollo segnacollo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.segnacollo
    ADD CONSTRAINT segnacollo_pk PRIMARY KEY (segnacollo);


--
-- TOC entry 5071 (class 2606 OID 50988)
-- Name: sessione_carico_scarico sessione_carico_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione_carico_scarico
    ADD CONSTRAINT sessione_carico_pk PRIMARY KEY (id);


--
-- TOC entry 5073 (class 2606 OID 50990)
-- Name: stato stato_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stato
    ADD CONSTRAINT stato_pk PRIMARY KEY (id);


--
-- TOC entry 5075 (class 2606 OID 51621)
-- Name: stato stato_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stato
    ADD CONSTRAINT stato_unique UNIQUE (descrizione);


--
-- TOC entry 5077 (class 2606 OID 50994)
-- Name: storico storico_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico
    ADD CONSTRAINT storico_pk PRIMARY KEY (id_movimento);


--
-- TOC entry 5031 (class 2606 OID 50996)
-- Name: contrassegno_tipo tipo_contrassegno_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contrassegno_tipo
    ADD CONSTRAINT tipo_contrassegno_pk PRIMARY KEY (id);


--
-- TOC entry 5059 (class 2606 OID 50998)
-- Name: rinnovo_assicurazione veicolo_assicurazione_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_assicurazione
    ADD CONSTRAINT veicolo_assicurazione_pk PRIMARY KEY (id);


--
-- TOC entry 5081 (class 2606 OID 51668)
-- Name: veicolo_lavoro veicolo_lavoro_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo_lavoro
    ADD CONSTRAINT veicolo_lavoro_pk PRIMARY KEY (targa);


--
-- TOC entry 5079 (class 2606 OID 51630)
-- Name: veicolo veicolo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo
    ADD CONSTRAINT veicolo_pk PRIMARY KEY (targa);


--
-- TOC entry 5082 (class 2606 OID 51003)
-- Name: adr_collo adr_collo_adr_elemento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adr_collo
    ADD CONSTRAINT adr_collo_adr_elemento_fk FOREIGN KEY (id_adr_elemento) REFERENCES public.adr_elemento(numero_un);


--
-- TOC entry 5083 (class 2606 OID 51008)
-- Name: adr_collo adr_collo_merce_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.adr_collo
    ADD CONSTRAINT adr_collo_merce_fk FOREIGN KEY (segnacollo) REFERENCES public.merce(segnacollo);


--
-- TOC entry 5084 (class 2606 OID 51013)
-- Name: assicurazione assicurazione_azienda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assicurazione
    ADD CONSTRAINT assicurazione_azienda_fk FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5085 (class 2606 OID 51018)
-- Name: autista autista_azienda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autista
    ADD CONSTRAINT autista_azienda_fk FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5086 (class 2606 OID 51023)
-- Name: autista autista_sede_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autista
    ADD CONSTRAINT autista_sede_fk FOREIGN KEY (sede) REFERENCES public.sede(id);


--
-- TOC entry 5087 (class 2606 OID 51028)
-- Name: autorizzazione_integrazione_indirizzo autorizzazione_indirizzo_azienda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autorizzazione_integrazione_indirizzo
    ADD CONSTRAINT autorizzazione_indirizzo_azienda_fk FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5088 (class 2606 OID 51033)
-- Name: autorizzazione_integrazione_indirizzo autorizzazione_indirizzo_storico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.autorizzazione_integrazione_indirizzo
    ADD CONSTRAINT autorizzazione_indirizzo_storico_fk FOREIGN KEY (id_movimento) REFERENCES public.storico(id_movimento);


--
-- TOC entry 5089 (class 2606 OID 51438)
-- Name: bordero bordero_autista_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero
    ADD CONSTRAINT bordero_autista_fk FOREIGN KEY (id_autista) REFERENCES public.autista(cod_fisc);


--
-- TOC entry 5090 (class 2606 OID 51043)
-- Name: bordero bordero_azienda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero
    ADD CONSTRAINT bordero_azienda_fk FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5092 (class 2606 OID 51048)
-- Name: bordero_merce bordero_merce_bordero_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero_merce
    ADD CONSTRAINT bordero_merce_bordero_fk FOREIGN KEY (id_bordero) REFERENCES public.bordero(id_bordero);


--
-- TOC entry 5093 (class 2606 OID 51053)
-- Name: bordero_merce bordero_merce_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero_merce
    ADD CONSTRAINT bordero_merce_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5094 (class 2606 OID 51058)
-- Name: bordero_merce bordero_merce_merce_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero_merce
    ADD CONSTRAINT bordero_merce_merce_fk FOREIGN KEY (segnacollo) REFERENCES public.merce(segnacollo);


--
-- TOC entry 5091 (class 2606 OID 51642)
-- Name: bordero bordero_veicolo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bordero
    ADD CONSTRAINT bordero_veicolo_fk FOREIGN KEY (id_macchina) REFERENCES public.veicolo(targa);


--
-- TOC entry 5095 (class 2606 OID 51505)
-- Name: cliente cliente_citta_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_citta_fk FOREIGN KEY (cap) REFERENCES public.citta(cap);


--
-- TOC entry 5096 (class 2606 OID 51073)
-- Name: cliente cliente_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5097 (class 2606 OID 51078)
-- Name: consegna_avvenuta consegna_avvenuta_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_avvenuta
    ADD CONSTRAINT consegna_avvenuta_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5098 (class 2606 OID 51083)
-- Name: consegna_avvenuta consegna_avvenuta_storico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_avvenuta
    ADD CONSTRAINT consegna_avvenuta_storico_fk FOREIGN KEY (id_movimento) REFERENCES public.storico(id_movimento);


--
-- TOC entry 5099 (class 2606 OID 51088)
-- Name: consegna_fallita consegna_fallita_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_fallita
    ADD CONSTRAINT consegna_fallita_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5100 (class 2606 OID 51093)
-- Name: consegna_fallita consegna_fallita_stato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_fallita
    ADD CONSTRAINT consegna_fallita_stato_fk FOREIGN KEY (id) REFERENCES public.stato(id);


--
-- TOC entry 5101 (class 2606 OID 51098)
-- Name: consegna_fallita consegna_fallita_storico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.consegna_fallita
    ADD CONSTRAINT consegna_fallita_storico_fk FOREIGN KEY (id_movimento) REFERENCES public.storico(id_movimento);


--
-- TOC entry 5102 (class 2606 OID 51103)
-- Name: epal_cliente epal_cliente_cliente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.epal_cliente
    ADD CONSTRAINT epal_cliente_cliente_fk FOREIGN KEY (id) REFERENCES public.cliente(id);


--
-- TOC entry 5103 (class 2606 OID 51108)
-- Name: epal_cliente epal_cliente_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.epal_cliente
    ADD CONSTRAINT epal_cliente_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5104 (class 2606 OID 51113)
-- Name: fasce_orarie fasce_orarie_giorno_preferito_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fasce_orarie
    ADD CONSTRAINT fasce_orarie_giorno_preferito_fk FOREIGN KEY (id) REFERENCES public.giorno_preferito(id);


--
-- TOC entry 5105 (class 2606 OID 51118)
-- Name: fasce_orarie_incarico fasce_orarie_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fasce_orarie_incarico
    ADD CONSTRAINT fasce_orarie_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5106 (class 2606 OID 51123)
-- Name: fasce_orarie_incarico fasce_orarie_incarico_fasce_orarie_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fasce_orarie_incarico
    ADD CONSTRAINT fasce_orarie_incarico_fasce_orarie_fk FOREIGN KEY (id) REFERENCES public.fasce_orarie(id);


--
-- TOC entry 5107 (class 2606 OID 51128)
-- Name: fasce_orarie_incarico fasce_orarie_incarico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fasce_orarie_incarico
    ADD CONSTRAINT fasce_orarie_incarico_fk FOREIGN KEY (incarico) REFERENCES public.incarico(ldv);


--
-- TOC entry 5119 (class 2606 OID 51133)
-- Name: incarico_sede_mitt_dest fk_ismd_dest; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT fk_ismd_dest FOREIGN KEY (sede_dest) REFERENCES public.cliente(id);


--
-- TOC entry 5120 (class 2606 OID 51138)
-- Name: incarico_sede_mitt_dest fk_ismd_incarico; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT fk_ismd_incarico FOREIGN KEY (incarico) REFERENCES public.incarico(ldv);


--
-- TOC entry 5121 (class 2606 OID 51143)
-- Name: incarico_sede_mitt_dest fk_ismd_mitt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT fk_ismd_mitt FOREIGN KEY (sede_mitt) REFERENCES public.cliente(id);


--
-- TOC entry 5108 (class 2606 OID 51148)
-- Name: giorno_fasce_orarie giorno_fasce_orarie_fasce_orarie_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.giorno_fasce_orarie
    ADD CONSTRAINT giorno_fasce_orarie_fasce_orarie_fk FOREIGN KEY (id_fascia_oraria) REFERENCES public.fasce_orarie(id);


--
-- TOC entry 5109 (class 2606 OID 51153)
-- Name: giorno_fasce_orarie giorno_fasce_orarie_giorno_preferito_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.giorno_fasce_orarie
    ADD CONSTRAINT giorno_fasce_orarie_giorno_preferito_fk FOREIGN KEY (id_giorno) REFERENCES public.giorno_preferito(id);


--
-- TOC entry 5110 (class 2606 OID 51158)
-- Name: incarico incarico_cliente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_cliente_fk FOREIGN KEY (mitt) REFERENCES public.cliente(id);


--
-- TOC entry 5111 (class 2606 OID 51163)
-- Name: incarico incarico_cliente_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_cliente_fk_1 FOREIGN KEY (dest) REFERENCES public.cliente(id);


--
-- TOC entry 5112 (class 2606 OID 51168)
-- Name: incarico incarico_cliente_vett_dest_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_cliente_vett_dest_fk FOREIGN KEY (vett_dest) REFERENCES public.cliente(id);


--
-- TOC entry 5116 (class 2606 OID 51173)
-- Name: incarico_contrassegno incarico_contrassegno_contrassegno_tipo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_contrassegno
    ADD CONSTRAINT incarico_contrassegno_contrassegno_tipo_fk FOREIGN KEY (contrassegno_tipo) REFERENCES public.contrassegno_tipo(id);


--
-- TOC entry 5117 (class 2606 OID 51178)
-- Name: incarico_contrassegno incarico_contrassegno_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_contrassegno
    ADD CONSTRAINT incarico_contrassegno_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5118 (class 2606 OID 51183)
-- Name: incarico_contrassegno incarico_contrassegno_incarico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_contrassegno
    ADD CONSTRAINT incarico_contrassegno_incarico_fk FOREIGN KEY (incarico) REFERENCES public.incarico(ldv);


--
-- TOC entry 5113 (class 2606 OID 51188)
-- Name: incarico incarico_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5114 (class 2606 OID 51193)
-- Name: incarico incarico_incarico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_incarico_fk FOREIGN KEY (child) REFERENCES public.incarico(ldv);


--
-- TOC entry 5122 (class 2606 OID 51198)
-- Name: incarico_sede_mitt_dest incarico_sede_mitt_dest_autorizzazione_indirizzo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT incarico_sede_mitt_dest_autorizzazione_indirizzo_fk FOREIGN KEY (id) REFERENCES public.autorizzazione_integrazione_indirizzo(id);


--
-- TOC entry 5123 (class 2606 OID 51203)
-- Name: incarico_sede_mitt_dest incarico_sede_mitt_dest_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico_sede_mitt_dest
    ADD CONSTRAINT incarico_sede_mitt_dest_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5115 (class 2606 OID 51208)
-- Name: incarico incarico_vett_mitt_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.incarico
    ADD CONSTRAINT incarico_vett_mitt_fk FOREIGN KEY (vett_mitt) REFERENCES public.cliente(id);


--
-- TOC entry 5124 (class 2606 OID 51213)
-- Name: manutenzione manutenzione_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manutenzione
    ADD CONSTRAINT manutenzione_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5125 (class 2606 OID 51218)
-- Name: manutenzione manutenzione_officina_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manutenzione
    ADD CONSTRAINT manutenzione_officina_fk FOREIGN KEY (id_officina) REFERENCES public.officina(id);


--
-- TOC entry 5126 (class 2606 OID 51632)
-- Name: manutenzione manutenzione_veicolo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manutenzione
    ADD CONSTRAINT manutenzione_veicolo_fk FOREIGN KEY (targa_veicolo) REFERENCES public.veicolo(targa);


--
-- TOC entry 5131 (class 2606 OID 51228)
-- Name: merce_epal merce_epal_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce_epal
    ADD CONSTRAINT merce_epal_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5132 (class 2606 OID 51233)
-- Name: merce_epal merce_epal_merce_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce_epal
    ADD CONSTRAINT merce_epal_merce_fk FOREIGN KEY (segnacollo) REFERENCES public.merce(segnacollo);


--
-- TOC entry 5127 (class 2606 OID 51238)
-- Name: merce merce_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce
    ADD CONSTRAINT merce_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5128 (class 2606 OID 51243)
-- Name: merce merce_incarico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce
    ADD CONSTRAINT merce_incarico_fk FOREIGN KEY (incarico) REFERENCES public.incarico(ldv);


--
-- TOC entry 5129 (class 2606 OID 51248)
-- Name: merce merce_merce_tipo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce
    ADD CONSTRAINT merce_merce_tipo_fk FOREIGN KEY (merce_tipo) REFERENCES public.merce_tipo(id);


--
-- TOC entry 5130 (class 2606 OID 51253)
-- Name: merce merce_segnacollo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merce
    ADD CONSTRAINT merce_segnacollo_fk FOREIGN KEY (segnacollo) REFERENCES public.segnacollo(segnacollo);


--
-- TOC entry 5133 (class 2606 OID 51556)
-- Name: officina officina_citta_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.officina
    ADD CONSTRAINT officina_citta_fk FOREIGN KEY (cap) REFERENCES public.citta(cap);


--
-- TOC entry 5134 (class 2606 OID 51263)
-- Name: officina officina_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.officina
    ADD CONSTRAINT officina_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5135 (class 2606 OID 51268)
-- Name: rinnovo_assicurazione rinnovo_assicurazione_assicurazione_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_assicurazione
    ADD CONSTRAINT rinnovo_assicurazione_assicurazione_fk FOREIGN KEY (id_assicurazione) REFERENCES public.assicurazione(id_assicurazione);


--
-- TOC entry 5136 (class 2606 OID 51273)
-- Name: rinnovo_assicurazione rinnovo_assicurazione_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_assicurazione
    ADD CONSTRAINT rinnovo_assicurazione_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5137 (class 2606 OID 51647)
-- Name: rinnovo_assicurazione rinnovo_assicurazione_veicolo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_assicurazione
    ADD CONSTRAINT rinnovo_assicurazione_veicolo_fk FOREIGN KEY (targa_veicolo) REFERENCES public.veicolo(targa);


--
-- TOC entry 5138 (class 2606 OID 51283)
-- Name: rinnovo_bollo rinnovo_bollo_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_bollo
    ADD CONSTRAINT rinnovo_bollo_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5139 (class 2606 OID 51652)
-- Name: rinnovo_bollo rinnovo_bollo_veicolo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_bollo
    ADD CONSTRAINT rinnovo_bollo_veicolo_fk FOREIGN KEY (targa_veicolo) REFERENCES public.veicolo(targa);


--
-- TOC entry 5140 (class 2606 OID 51586)
-- Name: rinnovo_patente rinnovo_patente_autista_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_patente
    ADD CONSTRAINT rinnovo_patente_autista_fk FOREIGN KEY (id_autista) REFERENCES public.autista(cod_fisc);


--
-- TOC entry 5141 (class 2606 OID 51298)
-- Name: rinnovo_patente rinnovo_patente_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rinnovo_patente
    ADD CONSTRAINT rinnovo_patente_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5142 (class 2606 OID 51303)
-- Name: riserva riserva_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.riserva
    ADD CONSTRAINT riserva_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5143 (class 2606 OID 51308)
-- Name: riserva riserva_merce_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.riserva
    ADD CONSTRAINT riserva_merce_fk FOREIGN KEY (segnacollo) REFERENCES public.merce(segnacollo);


--
-- TOC entry 5144 (class 2606 OID 51313)
-- Name: sede sede_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sede
    ADD CONSTRAINT sede_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5145 (class 2606 OID 51318)
-- Name: segnacollo segnacollo_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.segnacollo
    ADD CONSTRAINT segnacollo_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5146 (class 2606 OID 51323)
-- Name: sessione_carico_scarico sessione_carico_bordero_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione_carico_scarico
    ADD CONSTRAINT sessione_carico_bordero_fk FOREIGN KEY (id_bordero) REFERENCES public.bordero(id_bordero);


--
-- TOC entry 5147 (class 2606 OID 51328)
-- Name: sessione_carico_scarico sessione_carico_cliente_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione_carico_scarico
    ADD CONSTRAINT sessione_carico_cliente_fk FOREIGN KEY (vettore) REFERENCES public.cliente(id);


--
-- TOC entry 5148 (class 2606 OID 51333)
-- Name: sessione_carico_scarico sessione_carico_scarico_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione_carico_scarico
    ADD CONSTRAINT sessione_carico_scarico_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5149 (class 2606 OID 51338)
-- Name: sessione_carico_scarico sessione_carico_storico_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessione_carico_scarico
    ADD CONSTRAINT sessione_carico_storico_fk FOREIGN KEY (storico) REFERENCES public.storico(id_movimento);


--
-- TOC entry 5150 (class 2606 OID 51343)
-- Name: stato stato_stato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stato
    ADD CONSTRAINT stato_stato_fk FOREIGN KEY (parent) REFERENCES public.stato(id);


--
-- TOC entry 5151 (class 2606 OID 51348)
-- Name: storico storico_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico
    ADD CONSTRAINT storico_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5152 (class 2606 OID 51353)
-- Name: storico storico_sede_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico
    ADD CONSTRAINT storico_sede_fk FOREIGN KEY (sede) REFERENCES public.sede(id);


--
-- TOC entry 5153 (class 2606 OID 51358)
-- Name: storico storico_segnacollo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico
    ADD CONSTRAINT storico_segnacollo_fk FOREIGN KEY (segnacollo) REFERENCES public.segnacollo(segnacollo);


--
-- TOC entry 5154 (class 2606 OID 51363)
-- Name: storico storico_stato_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico
    ADD CONSTRAINT storico_stato_fk FOREIGN KEY (id_stato) REFERENCES public.stato(id);


--
-- TOC entry 5155 (class 2606 OID 51368)
-- Name: veicolo veicolo_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo
    ADD CONSTRAINT veicolo_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5157 (class 2606 OID 51373)
-- Name: veicolo_lavoro veicolo_lavoro_id_azienda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo_lavoro
    ADD CONSTRAINT veicolo_lavoro_id_azienda_fkey FOREIGN KEY (id_azienda) REFERENCES public.azienda(id);


--
-- TOC entry 5158 (class 2606 OID 51670)
-- Name: veicolo_lavoro veicolo_lavoro_veicolo_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo_lavoro
    ADD CONSTRAINT veicolo_lavoro_veicolo_fk FOREIGN KEY (targa) REFERENCES public.veicolo(targa);


--
-- TOC entry 5156 (class 2606 OID 51383)
-- Name: veicolo veicolo_sede_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.veicolo
    ADD CONSTRAINT veicolo_sede_fk FOREIGN KEY (sede) REFERENCES public.sede(id);


--
-- TOC entry 5353 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


-- Completed on 2026-03-04 07:55:30

--
-- PostgreSQL database dump complete
--

\unrestrict 93urzGAQw8n46agbVY0ndE0lKnlYZdzQV0fTDCRMMjL2qwflTcmrCLpzdKHfZsi

